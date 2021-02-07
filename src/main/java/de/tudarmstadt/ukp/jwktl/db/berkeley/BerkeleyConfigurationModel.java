/*******************************************************************************
 * Copyright 2013
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tudarmstadt.ukp.jwktl.db.berkeley;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sleepycat.compat.DbCompat;
import com.sleepycat.persist.model.AnnotationModel;
import com.sleepycat.persist.model.ClassMetadata;
import com.sleepycat.persist.model.EntityMetadata;
import com.sleepycat.persist.model.FieldMetadata;
import com.sleepycat.persist.model.KeyField;
import com.sleepycat.persist.model.NotPersistent;
import com.sleepycat.persist.model.NotTransient;
import com.sleepycat.persist.model.PrimaryKey;
import com.sleepycat.persist.model.PrimaryKeyMetadata;
import com.sleepycat.persist.model.Relationship;
import com.sleepycat.persist.model.SecondaryKey;
import com.sleepycat.persist.model.SecondaryKeyMetadata;

import de.tudarmstadt.ukp.jwktl.api.WiktionaryException;
import de.tudarmstadt.ukp.jwktl.api.entry.*;

public class BerkeleyConfigurationModel extends AnnotationModel {
	private static class EntityInfo {
		PrimaryKeyMetadata priKey;
		Map<String, SecondaryKeyMetadata> secKeys = new HashMap<String, SecondaryKeyMetadata>();
	}

	private Map<String, ClassMetadata> classMap;
	private Map<String, EntityInfo> entityMap;

	/**
	 * Constructs a model for annotated entity classes.
	 * @throws Exception 
	 */
	public BerkeleyConfigurationModel() throws WiktionaryException {
		super();
		classMap = new HashMap<String, ClassMetadata>();
		entityMap = new HashMap<String, EntityInfo>();

		addPersistentMetadata(Pronunciation.class.getName());
		addPersistentMetadata(Quotation.class.getName());
		addPersistentMetadata(WikiString.class.getName());
		addPersistentMetadata(WiktionaryEntry.class.getName());
		addPersistentMetadata(WiktionaryExample.class.getName());
		addPersistentMetadata(WiktionaryRelation.class.getName());
		addPersistentMetadata(WiktionarySense.class.getName());
		addPersistentMetadata(WiktionaryTranslation.class.getName());
		addPersistentMetadata(WiktionaryWordForm.class.getName());

		addClassMetadata(WiktionaryPage.class.getName());
	}

	@Override
	public synchronized ClassMetadata getClassMetadata(String className) {
		ClassMetadata classMetadata = super.getClassMetadata(className);
		if (classMetadata == null) {
			classMetadata = classMap.get(className);
		}
		return classMetadata;
	}

	@Override
	public synchronized EntityMetadata getEntityMetadata(String className) {
		EntityMetadata entityMetadata = super.getEntityMetadata(className);
		if (entityMetadata == null) {
			/* Return the collected entity metadata. */
			EntityInfo info = entityMap.get(className);
			if (info != null)
				entityMetadata = new EntityMetadata(className, info.priKey, Collections.unmodifiableMap(info.secKeys));

		}
		return entityMetadata;
	}

	private void addPersistentMetadata(String className) {
		ClassMetadata metadata = new ClassMetadata(className, 0, null, false, null, null, null, null);
		classMap.put(className, metadata);
		/* Add any new information about entities. */
		updateEntityInfo(metadata);
	}

	private ClassMetadata addClassMetadata(String className) throws WiktionaryException {
		boolean isEntity = true;
		int version = 0;
		String proxiedClassName = null;

		ClassMetadata metadata;
		Class<?> type;
		try {
			type = resolveClass(className);
		} catch (ClassNotFoundException e) {
			return null;
		}

		/* Get instance fields. */
		List<Field> fields = new ArrayList<Field>();
		boolean nonDefaultRules = getInstanceFields(fields, type);
		Collection<FieldMetadata> nonDefaultFields = null;
		if (nonDefaultRules) {
			nonDefaultFields = new ArrayList<FieldMetadata>(fields.size());
			for (Field field : fields) {
				nonDefaultFields.add(new FieldMetadata(field.getName(), field.getType().getName(), type.getName()));
			}
			nonDefaultFields = Collections.unmodifiableCollection(nonDefaultFields);
		}

		try {
			Field foundField = type.getDeclaredField("id");
			PrimaryKeyMetadata pkMD = new PrimaryKeyMetadata(foundField.getName(), foundField.getType().getName(), type.getName(), null);

			Map<String, SecondaryKeyMetadata> mapSK = new HashMap<String, SecondaryKeyMetadata>();

			Field field = WiktionaryPage.class.getDeclaredField("title");
			SecondaryKeyMetadata metadataSK = new SecondaryKeyMetadata(field.getName(), field.getType().getName(), type.getName(), null, field.getName(),
					Relationship.ONE_TO_ONE, null, null);
			mapSK.put(field.getName(), metadataSK);

			field = WiktionaryPage.class.getDeclaredField("normalizedTitle");
			metadataSK = new SecondaryKeyMetadata(field.getName(), field.getType().getName(), type.getName(), null, field.getName(), Relationship.MANY_TO_ONE, null, null);
			mapSK.put(field.getName(), metadataSK);

			
			/* Get the rest of the metadata and save it. */
			metadata = new ClassMetadata(className, version, proxiedClassName, isEntity, pkMD, Collections.unmodifiableMap(mapSK), null, nonDefaultFields);
			classMap.put(className, metadata);
			/* Add any new information about entities. */
			updateEntityInfo(metadata);
			
		} catch (NoSuchFieldException | SecurityException e) {
			throw new WiktionaryException(e);
		}

		return metadata;
	}

	/**
	 * Fills in the fields array and returns true if the default rules for field
	 * persistence were overridden.
	 */
	private boolean getInstanceFields(List<Field> fields, Class<?> type) {
		boolean nonDefaultRules = false;
		for (Field field : type.getDeclaredFields()) {
			boolean notPersistent = (field.getAnnotation(NotPersistent.class) != null);
			boolean notTransient = (field.getAnnotation(NotTransient.class) != null);
			if (notPersistent && notTransient) {
				throw new IllegalArgumentException("Both @NotTransient and @NotPersistent not allowed");
			}
			if (notPersistent || notTransient) {
				nonDefaultRules = true;
			}
			int mods = field.getModifiers();

			if (!Modifier.isStatic(mods) && !notPersistent && (!Modifier.isTransient(mods) || notTransient)) {
				/* Field is DPL persistent. */
				fields.add(field);
			} else {
				/* If non-persistent, no other annotations should be used. */
				if (field.getAnnotation(PrimaryKey.class) != null || field.getAnnotation(SecondaryKey.class) != null || field.getAnnotation(KeyField.class) != null) {
					throw new IllegalArgumentException("@PrimaryKey, @SecondaryKey and @KeyField not " + "allowed on non-persistent field");
				}
			}
		}
		return nonDefaultRules;
	}

	/**
	 * Add newly discovered metadata to our stash of entity info. This info is
	 * maintained as it is discovered because it would be expensive to create it
	 * on demand -- all class metadata would have to be traversed.
	 */
	private void updateEntityInfo(ClassMetadata metadata) {

		/*
		 * Find out whether this class or its superclass is an entity. In the
		 * process, traverse all superclasses to load their metadata -- this
		 * will populate as much entity info as possible.
		 */
		String entityClass = null;
		PrimaryKeyMetadata priKey = null;
		Map<String, SecondaryKeyMetadata> secKeys = new HashMap<String, SecondaryKeyMetadata>();
		for (ClassMetadata data = metadata; data != null;) {
			if (data.isEntityClass()) {
				if (entityClass != null) {
					throw new IllegalArgumentException("An entity class may not be derived from another" + " entity class: " + entityClass + ' ' + data.getClassName());
				}
				entityClass = data.getClassName();
			}
			/* Save first primary key encountered. */
			if (priKey == null) {
				priKey = data.getPrimaryKey();
			}
			/* Save all secondary keys encountered by key name. */
			Map<String, SecondaryKeyMetadata> classSecKeys = data.getSecondaryKeys();
			if (classSecKeys != null) {
				for (SecondaryKeyMetadata secKey : classSecKeys.values()) {
					secKeys.put(secKey.getKeyName(), secKey);
				}
			}
			/* Load superclass metadata. */
			Class<?> cls;
			try {
				cls = resolveClass(data.getClassName());
			} catch (ClassNotFoundException e) {
				throw DbCompat.unexpectedException(e);
			}
			cls = cls.getSuperclass();
			if (cls != Object.class) {
				data = getClassMetadata(cls.getName());
				if (data == null) {
					throw new IllegalArgumentException("Persistent class has non-persistent superclass: " + cls.getName());
				}
			} else {
				data = null;
			}
		}

		/* Add primary and secondary key entity info. */
		if (entityClass != null) {
			EntityInfo info = entityMap.get(entityClass);
			if (info == null) {
				info = new EntityInfo();
				entityMap.put(entityClass, info);
			}
			if (priKey == null) {
				throw new IllegalArgumentException("Entity class has no primary key: " + entityClass);
			}
			info.priKey = priKey;
			info.secKeys.putAll(secKeys);
		}
	}
}

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
package de.tudarmstadt.ukp.jwktl.api.entry;

import com.sleepycat.persist.model.Persistent;

import de.tudarmstadt.ukp.jwktl.api.IWiktionaryRelation;
import de.tudarmstadt.ukp.jwktl.api.RelationType;

/**
 * Default implementation of the {@link IWiktionaryRelation} interface.
 * See there for details.
 * @author Christian M. Meyer
 */
@Persistent
public class WiktionaryRelation implements IWiktionaryRelation {

	protected String target;
	protected RelationType type;
	protected String targetSense;
	protected LinkType linkType;
	//protected RelationSourceType relationSourceType;
	
	/** Creates a new, empty relation. */ 
	public WiktionaryRelation() {}
	
	/** Creates a new relation for the given target and relation type. */
	public WiktionaryRelation(final String target, final RelationType type) {
		this.target = target;
		this.type = type;
		//this.relationSourceType = RelationSourceType.ENTRY;
	}

	public RelationType getRelationType() {
		return type;
	}

	public String getTarget() {
		return target;
	}

	public String getTargetSense() {
		return targetSense;
	}

	/** Specifies additional information on the target word sense. */
	public void setTargetSense(final String targetSense){
		this.targetSense = targetSense;
	}
	
	public LinkType getLinkType() {
		return linkType;
	}
	
	/** Assigns a new link type for this relation. */
	public void setLinkType(final LinkType linkType) {
		this.linkType = linkType;
	}

	@Override
	public String toString() {
		return type + ":" + target;
	}
	
}

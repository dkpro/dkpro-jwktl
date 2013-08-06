/*******************************************************************************
 * Copyright 2008 Andrew Krizhanovsky <andrew.krizhanovsky at gmail.com>
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
package de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.multi.ru.name;


import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.constant.Relation;
import de.tudarmstadt.ukp.jwktl.parser.ru.wikokit.base.wikt.constant.RelationLocal;


/** Names of semantic relations in Russian and the links to the Relation objects.
 * 
 * @attention: initialize class before using, e.g. "RelationLocal _ = RelationRu.synonymy;"
 */
public class RelationRu extends RelationLocal {

    private RelationRu(String _name, String _short_name, Relation _rel) {
        super(_name, _short_name, _rel);
    }
    
    public static final RelationLocal   synonymy,
                                        antonymy,
                                        hypernymy,
                                        hyponymy,
                                        holonymy,
                                        meronymy,
                                        troponymy,
                                        coordinate_term,
                                        otherwise_related;
    
    static {
    // public static final RelationLocal unknown = new RelationRu("неизвестные", "неизв.", Relation.uknown); /** The relation is unknown :( */
    
        synonymy = new RelationRu("синонимы", "син.", Relation.synonymy);
        antonymy = new RelationRu("антонимы", "ант.", Relation.antonymy);
        
        hypernymy = new RelationRu("гиперонимы", "гиперн.", Relation.hypernymy);
        hyponymy = new RelationRu("гипонимы", "гипон.", Relation.hyponymy);
        
        holonymy = new RelationRu("холонимы", "холон.", Relation.holonymy);    
        meronymy = new RelationRu("меронимы", "мерон.", Relation.meronymy);
        
        troponymy = new RelationRu("тропонимы", "тропон.", Relation.troponymy);
        coordinate_term = new RelationRu("согипонимы", "согип.", Relation.coordinate_term);
        
        otherwise_related = new RelationRu("смотри также", "см.", Relation.otherwise_related);
    }
}

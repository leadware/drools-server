//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.04 at 10:12:55 AM GMT 
//


package net.leadware.drools.server.model.configuration;

/*
 * #%L
 * DROOLS SERVER :: Model
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2013 - 2014 Leadware
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KnowledgeSessionsConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KnowledgeSessionsConfiguration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="knowledge-session" type="{http://www.leadware.net/drools-server-configuration}KnowledgeSessionConfiguration" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KnowledgeSessionsConfiguration", namespace = "http://www.leadware.net/drools-server-configuration", propOrder = {
    "knowledgeSession"
})
public class KnowledgeSessionsConfiguration {

    @XmlElement(name = "knowledge-session", namespace = "http://www.leadware.net/drools-server-configuration", required = true)
    protected List<KnowledgeSessionConfiguration> knowledgeSession;

    /**
     * Gets the value of the knowledgeSession property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the knowledgeSession property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKnowledgeSession().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KnowledgeSessionConfiguration }
     * 
     * 
     */
    public List<KnowledgeSessionConfiguration> getKnowledgeSession() {
        if (knowledgeSession == null) {
            knowledgeSession = new ArrayList<KnowledgeSessionConfiguration>();
        }
        return this.knowledgeSession;
    }

}

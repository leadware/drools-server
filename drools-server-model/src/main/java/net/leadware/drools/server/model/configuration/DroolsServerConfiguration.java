//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.02 at 09:41:53 AM GMT 
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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DroolsServerConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DroolsServerConfiguration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resources" type="{http://www.leadware.net/drools-server-configuration}ResourcesConfiguration"/>
 *         &lt;element name="knowledge-bases" type="{http://www.leadware.net/drools-server-configuration}KnowledgeBasesConfiguration"/>
 *         &lt;element name="knowledge-agents" type="{http://www.leadware.net/drools-server-configuration}KnowledgeAgentsConfiguration" minOccurs="0"/>
 *         &lt;element name="knowledge-sessions" type="{http://www.leadware.net/drools-server-configuration}KnowledgeSessionsConfiguration" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DroolsServerConfiguration", namespace = "http://www.leadware.net/drools-server-configuration", propOrder = {
    "resources",
    "knowledgeBases",
    "knowledgeAgents",
    "knowledgeSessions"
})
public class DroolsServerConfiguration {

    @XmlElement(namespace = "http://www.leadware.net/drools-server-configuration", required = true)
    protected ResourcesConfiguration resources;
    @XmlElement(name = "knowledge-bases", namespace = "http://www.leadware.net/drools-server-configuration", required = true)
    protected KnowledgeBasesConfiguration knowledgeBases;
    @XmlElement(name = "knowledge-agents", namespace = "http://www.leadware.net/drools-server-configuration")
    protected KnowledgeAgentsConfiguration knowledgeAgents;
    @XmlElement(name = "knowledge-sessions", namespace = "http://www.leadware.net/drools-server-configuration")
    protected KnowledgeSessionsConfiguration knowledgeSessions;

    /**
     * Gets the value of the resources property.
     * 
     * @return
     *     possible object is
     *     {@link ResourcesConfiguration }
     *     
     */
    public ResourcesConfiguration getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourcesConfiguration }
     *     
     */
    public void setResources(ResourcesConfiguration value) {
        this.resources = value;
    }

    /**
     * Gets the value of the knowledgeBases property.
     * 
     * @return
     *     possible object is
     *     {@link KnowledgeBasesConfiguration }
     *     
     */
    public KnowledgeBasesConfiguration getKnowledgeBases() {
        return knowledgeBases;
    }

    /**
     * Sets the value of the knowledgeBases property.
     * 
     * @param value
     *     allowed object is
     *     {@link KnowledgeBasesConfiguration }
     *     
     */
    public void setKnowledgeBases(KnowledgeBasesConfiguration value) {
        this.knowledgeBases = value;
    }

    /**
     * Gets the value of the knowledgeAgents property.
     * 
     * @return
     *     possible object is
     *     {@link KnowledgeAgentsConfiguration }
     *     
     */
    public KnowledgeAgentsConfiguration getKnowledgeAgents() {
        return knowledgeAgents;
    }

    /**
     * Sets the value of the knowledgeAgents property.
     * 
     * @param value
     *     allowed object is
     *     {@link KnowledgeAgentsConfiguration }
     *     
     */
    public void setKnowledgeAgents(KnowledgeAgentsConfiguration value) {
        this.knowledgeAgents = value;
    }

    /**
     * Gets the value of the knowledgeSessions property.
     * 
     * @return
     *     possible object is
     *     {@link KnowledgeSessionsConfiguration }
     *     
     */
    public KnowledgeSessionsConfiguration getKnowledgeSessions() {
        return knowledgeSessions;
    }

    /**
     * Sets the value of the knowledgeSessions property.
     * 
     * @param value
     *     allowed object is
     *     {@link KnowledgeSessionsConfiguration }
     *     
     */
    public void setKnowledgeSessions(KnowledgeSessionsConfiguration value) {
        this.knowledgeSessions = value;
    }

}

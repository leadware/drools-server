//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.24 at 05:42:26 PM GMT 
//


package net.leadware.drools.server.model.configuration;

/*
 * #%L
 * DROOLS SERVER :: Model
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2013 Leadware
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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.leadware.drools.server.model.configuration package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DroolsServer_QNAME = new QName("http://www.example.org/drools-server-configuration", "drools-server");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.leadware.drools.server.model.configuration
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link KnowledgeSession }
     * 
     */
    public KnowledgeSession createKnowledgeSession() {
        return new KnowledgeSession();
    }

    /**
     * Create an instance of {@link KnowledgeAgent }
     * 
     */
    public KnowledgeAgent createKnowledgeAgent() {
        return new KnowledgeAgent();
    }

    /**
     * Create an instance of {@link DroolsServerConfiguration }
     * 
     */
    public DroolsServerConfiguration createDroolsServerConfiguration() {
        return new DroolsServerConfiguration();
    }

    /**
     * Create an instance of {@link Resource }
     * 
     */
    public Resource createResource() {
        return new Resource();
    }

    /**
     * Create an instance of {@link KnowledgeBase }
     * 
     */
    public KnowledgeBase createKnowledgeBase() {
        return new KnowledgeBase();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DroolsServerConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.example.org/drools-server-configuration", name = "drools-server")
    public JAXBElement<DroolsServerConfiguration> createDroolsServer(DroolsServerConfiguration value) {
        return new JAXBElement<DroolsServerConfiguration>(_DroolsServer_QNAME, DroolsServerConfiguration.class, null, value);
    }

}

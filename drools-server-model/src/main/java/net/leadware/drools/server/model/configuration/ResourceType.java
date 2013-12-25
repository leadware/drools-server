//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.25 at 05:14:18 PM GMT 
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

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResourceType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ResourceType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="DRL"/>
 *     &lt;enumeration value="XDRL"/>
 *     &lt;enumeration value="DSL"/>
 *     &lt;enumeration value="DSLR"/>
 *     &lt;enumeration value="DRF"/>
 *     &lt;enumeration value="DTABLE"/>
 *     &lt;enumeration value="BRL"/>
 *     &lt;enumeration value="PKG"/>
 *     &lt;enumeration value="PMML"/>
 *     &lt;enumeration value="XSD"/>
 *     &lt;enumeration value="DESCR"/>
 *     &lt;enumeration value="CHANGE_SET"/>
 *     &lt;enumeration value="BPMN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ResourceType", namespace = "http://www.leadware.net/drools-server-configuration")
@XmlEnum
public enum ResourceType {

    DRL,
    XDRL,
    DSL,
    DSLR,
    DRF,
    DTABLE,
    BRL,
    PKG,
    PMML,
    XSD,
    DESCR,
    CHANGE_SET,
    BPMN;

    public String value() {
        return name();
    }

    public static ResourceType fromValue(String v) {
        return valueOf(v);
    }

}

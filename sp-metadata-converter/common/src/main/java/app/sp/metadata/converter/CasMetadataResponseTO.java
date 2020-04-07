/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package app.sp.metadata.converter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "casMetadataResponse")
@XmlType
public class CasMetadataResponseTO implements Serializable {

    private static final long serialVersionUID = 5461846770586031758L;

    /*
     * The identifier of the record.
     */
    private Integer id;

    /*
     * Indexed field which describes and names the metadata briefly.
     */
    private String name;

    /*
     * The XML document representing the metadata for the service provider.
     */
    private String value;

    /*
     * The contents of the signing certificate to validate metadata, if any.
     */
    private String signature;

    @JsonCreator
    public CasMetadataResponseTO(
            @JsonProperty(value = "id", required = true) final Integer id,
            @JsonProperty(value = "name", required = false) final String name,
            @JsonProperty(value = "signature", required = false) final String signature,
            @JsonProperty(value = "value", required = false) final String value) {

        this.id = id;
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public CasMetadataResponseTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(final String specification) {
        this.signature = specification;
    }

}

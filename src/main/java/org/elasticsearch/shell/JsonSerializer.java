/*
 * Licensed to Luca Cavanna (the "Author") under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Elastic Search licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.shell;

/**
 * @author Luca Cavanna
 *
 * Creates a json native object from a string and the other way around
 * @param <JsonInput> representation of a json object received as input
 * @param <JsonOutput> representation of a json object produced as output
 */
public interface JsonSerializer<JsonInput, JsonOutput> {
    /**
     * Creates a string given a native json object
     * @param json the given native json object
     * @return the created string
     */
    public String jsonToString(JsonInput json);

    /**
     * Creates a native json object given a string
     * @param json the given native json object
     * @return the created native json object
     */
    public JsonOutput stringToJson(String json);
}
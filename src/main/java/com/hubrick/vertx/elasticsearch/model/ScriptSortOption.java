/**
 * Copyright (C) 2016 Etaia AS (oss@hubrick.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hubrick.vertx.elasticsearch.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.core.json.JsonObject;
import org.elasticsearch.search.sort.SortOrder;

import java.util.HashMap;
import java.util.Map;

/**
 * Sort option
 */
@DataObject
public class ScriptSortOption extends BaseSortOption {

    private String script;
    private Type type;
    private String lang;
    private JsonObject params = new JsonObject();

    public static final String JSON_FIELD_SCRIPT = "script";
    public static final String JSON_FIELD_LANG = "lang";
    public static final String JSON_FIELD_TYPE = "type";
    public static final String JSON_FIELD_PARAMS = "params";

    public ScriptSortOption() {
        super(SortType.SCRIPT);
    }

    public ScriptSortOption(ScriptSortOption other) {
        super(other);
        script = other.getScript();
        lang = other.getLang();
        type = other.getType();
        params = other.getParams();
    }

    public ScriptSortOption(JsonObject json) {
        super(json);

        script = json.getString(JSON_FIELD_SCRIPT);
        lang = json.getString(JSON_FIELD_LANG);
        type = Type.fromString(json.getString(JSON_FIELD_TYPE));
        params = json.getJsonObject(JSON_FIELD_PARAMS);
    }

    public String getScript() {
        return script;
    }

    public ScriptSortOption setScript(String script) {
        this.script = script;
        return this;
    }

    public Type getType() {
        return type;
    }

    public ScriptSortOption setType(Type type) {
        this.type = type;
        return this;
    }

    public String getLang() {
        return lang;
    }

    public ScriptSortOption setLang(String lang) {
        this.lang = lang;
        return this;
    }

    @GenIgnore
    public ScriptSortOption addParam(String name, String value) {
        params.put(name, value);
        return this;
    }

    public JsonObject getParams() {
        return params;
    }

    public ScriptSortOption setParams(JsonObject params) {
        this.params = params;
        return this;
    }

    @Override
    public ScriptSortOption setOrder(SortOrder order) {
        super.setOrder(order);
        return this;
    }

    public JsonObject toJson() {
        final JsonObject baseJsonObject = super.toJson();

        final JsonObject jsonObject = new JsonObject()
                .put(JSON_FIELD_SCRIPT, script)
                .put(JSON_FIELD_LANG, lang)
                .put(JSON_FIELD_TYPE, type.getValue())
                .put(JSON_FIELD_PARAMS, params);

        jsonObject.mergeIn(baseJsonObject);
        return jsonObject;
    }

    public enum Type {
        NUMBER("number"),
        STRING("string");

        private static final Map<String, Type> REVERSE_LOOKUP = new HashMap();
        static {
            for(Type type : values()) {
                REVERSE_LOOKUP.put(type.getValue(), type);
            }
        }

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static Type fromString(String value) {
            return REVERSE_LOOKUP.get(value);
        }
    }

}

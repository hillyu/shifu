/*
 * Copyright [2013-2016] PayPal Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ml.shifu.shifu.core.pmml.builder.impl;

import ml.shifu.shifu.container.obj.ColumnConfig;
import ml.shifu.shifu.container.obj.ModelConfig;
import ml.shifu.shifu.core.pmml.builder.creator.AbstractPmmlElementCreator;
import org.dmg.pmml.DataDictionary;
import org.dmg.pmml.DataField;
import org.dmg.pmml.FieldName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanhu on 3/29/16.
 */
public class DataDictionaryCreator extends AbstractPmmlElementCreator<DataDictionary> {

    public DataDictionaryCreator(ModelConfig modelConfig, List<ColumnConfig> columnConfigList) {
        super(modelConfig, columnConfigList);
    }

    public DataDictionaryCreator(ModelConfig modelConfig, List<ColumnConfig> columnConfigList, boolean isConcise) {
        super(modelConfig, columnConfigList, isConcise);
    }

    @Override
    public DataDictionary build() {
        DataDictionary dict = new DataDictionary();
        List<DataField> fields = new ArrayList<DataField>();

        for(ColumnConfig columnConfig: columnConfigList) {
            if ( isConcise ) {
                if ( columnConfig.isFinalSelect() || columnConfig.isTarget() ) {
                    fields.add(convertColumnToDataField(columnConfig));
                } // else ignore
            } else {
                fields.add(convertColumnToDataField(columnConfig));
            }
        }

        dict.withDataFields(fields);
        dict.withNumberOfFields(fields.size());
        return dict;
    }

    private DataField convertColumnToDataField(ColumnConfig columnConfig) {
        DataField field = new DataField();
        field.setName(FieldName.create(columnConfig.getColumnName()));
        field.setOptype(getOptype(columnConfig));
        field.setDataType(getDataType(field.getOptype()));
        return field;
    }

}
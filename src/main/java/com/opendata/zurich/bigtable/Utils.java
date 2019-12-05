package com.opendata.zurich.bigtable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.beam.sdk.values.KV;

import com.google.api.services.bigquery.model.TableRow;
import com.google.bigtable.v2.Mutation;
import com.google.protobuf.ByteString;

public class Utils {

	public static KV<ByteString, Iterable<Mutation>> makeWrite(String key, TableRow tableRow, String familyName) {
		List<Mutation> mutations = new ArrayList<>();
		for (Map.Entry<String, Object> field : tableRow.entrySet()) {
            Mutation m = Mutation.newBuilder()
            .setSetCell(
                Mutation.SetCell.newBuilder()
                    .setValue(ByteString.copyFromUtf8(String.valueOf(field.getValue())))
                    .setFamilyName(familyName == null ? field.getKey() : familyName))
            .build();
            mutations.add(m);
		}
		ByteString rowKey = ByteString.copyFromUtf8(key);
		return KV.of(rowKey, mutations);
	}
	
}

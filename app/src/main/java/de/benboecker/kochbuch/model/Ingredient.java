package de.benboecker.kochbuch.model;

import java.util.Random;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Benni on 19.11.16.
 */

public class Ingredient extends RealmObject {
	@PrimaryKey
	private long id = 0;
	private String name = "";
	private String unit = "";
	private long quantity = 0;


	public static Ingredient newIngredient() {
		Ingredient ingredient = null;

		Realm realm = Realm.getDefaultInstance();
		if (!realm.isInTransaction()) {
			realm.beginTransaction();
			ingredient = realm.createObject(Ingredient.class, RealmHelper.getNextID(Ingredient.class, "id"));
			realm.commitTransaction();
		}

		return ingredient;
	}


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
}

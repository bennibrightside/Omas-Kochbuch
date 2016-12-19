package de.benboecker.kochbuch.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import de.benboecker.kochbuch.R;
import de.benboecker.kochbuch.adapters.IngredientAdapter;
import de.benboecker.kochbuch.fragments.IngredientDialogFragment;
import de.benboecker.kochbuch.fragments.TextInputDialogFragment;
import de.benboecker.kochbuch.model.Ingredient;
import de.benboecker.kochbuch.model.RealmIndex;
import de.benboecker.kochbuch.model.Recipe;

public class IngredientListActivity extends RealmActivity implements AdapterView.OnItemClickListener, View.OnClickListener, IngredientDialogFragment.IngredientDialogListener {

	private ListView listView;
	private Recipe recipe;
	private IngredientAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingedient_list);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (adapter == null) {
			setupData();
			setupInterface();
		}
	}

	@Override
	public void onClick(View view) {
		IngredientDialogFragment ingredientDialogFragment = new IngredientDialogFragment();
		ingredientDialogFragment.show(getFragmentManager(), "ingredient");
	}

	@Override
	public void onNewIngredient(Ingredient ingredient) {
		recipe.getIngredients().add(ingredient);
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Ingredient ingredient = this.adapter.getItem(i);

		IngredientDialogFragment ingredientDialogFragment = new IngredientDialogFragment();
		ingredientDialogFragment.setIngredient(ingredient);
		ingredientDialogFragment.show(getFragmentManager(), "ingredient");
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		if (v.getId() == R.id.list_view) {
			menu.setHeaderTitle(R.string.options);
			menu.add(Menu.NONE, 0, 0, R.string.delete);
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		super.onContextItemSelected(item);

		final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

		if (item.getItemId() == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Wirklich löschen?");
			builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {

					Ingredient ingredient = adapter.getItem(info.position);

					realm.beginTransaction();
					recipe.getIngredients().remove(ingredient);
					realm.commitTransaction();
					IngredientListActivity.this.adapter.notifyDataSetChanged();

				}
			}).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {}
			});
			builder.create().show();
		}

		return true;
	}

	private void setupInterface() {
		listView = (ListView) this.findViewById(R.id.list_view);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		listView.invalidate();
		registerForContextMenu(listView);

		Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		this.setActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) this.findViewById(R.id.fab);
		fab.setOnClickListener(IngredientListActivity.this);
	}

	private void setupData() {
		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			long recipeID = extras.getLong("id");
			recipe = realm.where(Recipe.class).equalTo("id", recipeID).findFirst();
			adapter = new IngredientAdapter(this, recipe.getIngredients());
		}
	}


}

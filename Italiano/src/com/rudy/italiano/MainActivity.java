package com.rudy.italiano;

import org.w3c.dom.Text;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {
	private final static int EDIT_ID = 1;
	private TestType testType;
	private int aantal, teller, score;
	private TestData opdracht;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		((Button) findViewById(R.id.btnVolgende)).setEnabled(false);
		((Button) findViewById(R.id.btnGoedkeuren)).setEnabled(false);
		((ImageButton) findViewById(R.id.imgGoedFout)).setEnabled(false);
		((ImageButton) findViewById(R.id.imgScore)).setEnabled(false);
		((TextView) findViewById(R.id.txtVraag)).setText("Kies een test en druk op de Start knop");
		this.InitOplossing();
		try {
			// this.maxAantal =
			// TestController.getInstance().ReadTestData(TestType.VERVOEGINGEN);
			// testData = TestController.getInstance().GetOpdracht();
			// findViewById(R.id.txtVraag)).setText(testData.getVraag());
		} catch (Exception e) {
			this.showAlert(e.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		// return true;
		menu.add(Menu.NONE, EDIT_ID, Menu.NONE, "Instellingen").setIcon(R.drawable.ic_launcher)
				.setAlphabeticShortcut('e');
		return (super.onCreateOptionsMenu(menu));
	}

	@Override
	protected void onResume() {
		SharedPreferences prefs;
		String dataPath;

		super.onResume();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		dataPath = prefs.getString("dataPath", "/mnt/sdcard/Data/Italiano/");
		TestController.getInstance().SetDataPath(dataPath);
		String test = prefs.getString("list", "VERTALINGEN");
		if (test.equals("VERTALINGEN")) {
			this.testType = TestType.VERTALINGEN;
		} else if (test.equals("MET_AFLEIDINGEN")) {
			this.testType = TestType.MET_AFLEIDINGEN;
		} else if (test.equals("VERVOEGINGEN")) {
			this.testType = TestType.VERVOEGINGEN;
		} else {
			this.testType = TestType.ALLES;
		}

		try {
			this.aantal = TestController.getInstance().ReadTestData(this.testType);

			((Button) findViewById(R.id.btnVolgende)).setEnabled(true);
			((Button) findViewById(R.id.btnGoedkeuren)).setEnabled(false);
			this.InitOplossing();
			this.teller = 0;
			this.score = 0;
			this.OpvullenOpdracht();
		} catch (Exception e) {
			this.showAlert(e.getMessage());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case EDIT_ID:
			startActivity(new Intent(this, EditPreferences.class));
			return (true);
		}
		return (super.onOptionsItemSelected(item));
	}

	private void InitOplossing() {
		((TextView) findViewById(R.id.txtVorigVraag)).setText("");
		((ImageButton) findViewById(R.id.imgGoedFout)).setImageResource(R.drawable.ic_action_good);
		((ImageButton) findViewById(R.id.imgGoedFout)).setVisibility(View.INVISIBLE);
		((TextView) findViewById(R.id.txtOplossing)).setText("");
		((TextView) findViewById(R.id.txtScore)).setText("");
		((ImageButton) findViewById(R.id.imgScore)).setImageResource(R.drawable.ic_action_good);
		((ImageButton) findViewById(R.id.imgScore)).setVisibility(View.INVISIBLE);
	}

	public void btnStartClick(View view) {

	}

	public void btnStopClick(View view) {
	}

	public void btnVolgendeClick(View view) {
		try {
			if (((EditText) findViewById(R.id.edtAntwoord)).getText().toString().equals(this.opdracht.getAntwoord())) {
				((TextView) findViewById(R.id.txtOplossing)).setText(((EditText) findViewById(R.id.edtAntwoord))
						.getText().toString() + " is juist");
				((ImageButton) findViewById(R.id.imgGoedFout)).setImageResource(R.drawable.ic_action_good);
				((Button) findViewById(R.id.btnGoedkeuren)).setEnabled(false);
				this.score++;
			} else {
				((TextView) findViewById(R.id.txtOplossing)).setText(((EditText) findViewById(R.id.edtAntwoord))
						.getText().toString() + " is fout, het juiste antwoord is " + this.opdracht.getAntwoord());
				((ImageButton) findViewById(R.id.imgGoedFout)).setImageResource(R.drawable.ic_action_bad);
				((Button) findViewById(R.id.btnGoedkeuren)).setEnabled(true);
			}

			this.teller++;
			((ImageButton) findViewById(R.id.imgGoedFout)).setVisibility(View.VISIBLE);
			((TextView) findViewById(R.id.txtVorigVraag)).setText(((TextView) findViewById(R.id.txtVraag)).getText());
			((TextView) findViewById(R.id.txtScore)).setText("Score: " + this.score + " / " + this.teller + " ("
					+ String.format("%1$.2f", this.score * 100.0 / this.teller) + " %)");
			((ImageButton) findViewById(R.id.imgScore))
					.setImageResource(this.score > (this.teller / 2.0) ? R.drawable.ic_action_good
							: R.drawable.ic_action_bad);
			((ImageButton) findViewById(R.id.imgGoedFout)).setVisibility(View.VISIBLE);
			((ImageButton) findViewById(R.id.imgScore)).setVisibility(View.VISIBLE);

			if (this.teller < this.aantal) {
				this.OpvullenOpdracht();
			} else {
				((Button) findViewById(R.id.btnVolgende)).setEnabled(false);
			}
		} catch (Exception e) {
			this.showAlert(e.getMessage());
		}
	}

	public void btnGoedkeurenClick(View view) {
		((ImageButton) findViewById(R.id.imgGoedFout)).setImageResource(R.drawable.ic_action_good);
		((ImageButton) findViewById(R.id.imgGoedFout)).setVisibility(View.VISIBLE);
		this.score++;
		((TextView) findViewById(R.id.txtScore)).setText("Score: " + this.score + " / " + this.teller + " ("
				+ String.format("%1$.2f", this.score * 100.0 / this.teller) + " %)");
		((ImageButton) findViewById(R.id.imgScore))
				.setImageResource(this.score > (this.teller / 2.0) ? R.drawable.ic_action_good
						: R.drawable.ic_action_bad);
		((ImageButton) findViewById(R.id.imgGoedFout)).setVisibility(View.VISIBLE);
		((ImageButton) findViewById(R.id.imgScore)).setVisibility(View.VISIBLE);
		((Button) findViewById(R.id.btnGoedkeuren)).setEnabled(false);
		((EditText) findViewById(R.id.edtAntwoord)).requestFocus();
	}

	private void OpvullenOpdracht() throws Exception {
		this.opdracht = TestController.getInstance().GetOpdracht();
		((TextView) findViewById(R.id.txtVraag)).setText(this.opdracht.getVraag());
		((EditText) findViewById(R.id.edtAntwoord)).setText("");
		((EditText) findViewById(R.id.edtAntwoord)).requestFocus();
	}

	private void showAlert(String aMessage) {
		new AlertDialog.Builder(this).setTitle(R.string.fout).setMessage(aMessage)
				.setPositiveButton(R.string.sluiten, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dlg, int sumthin) {
						return;
					}
				}).show();
	}
}

package com.rudy.italiano;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.SharedPreferences;
import android.util.Log;

public class TestController {
	private static TestController instance = null;
	private int aantalIds;
	private ArrayList<TestData> dataArray;
	private Random random;
	private String dataPath;
	private ArrayList<Integer> historiek;

	protected TestController() {
		super();
	}

	public static TestController getInstance() {
		if (instance == null) {
			TestController.instance = new TestController();
		}

		return instance;
	}

	public void ReadSettings(SharedPreferences aPrefs) {
		this.dataPath = aPrefs.getString("dataPath", "/mnt/sdcard/Data/Italiano/");
//		this.dataPath = aPrefs.getString("dataPath", "/storage/extSdCard/Data/Italiano/");
	}

	public ArrayList<TestData> getDataArray() {
		return dataArray;
	}

	public int ReadTestData(TestType aTestType) throws Exception {
		Hashtable<Long, Element> infinitieven;

		this.dataArray = new ArrayList<TestData>();
		this.historiek = new ArrayList<Integer>();
		this.aantalIds = 0;

		try {
			switch (aTestType) {
			case VERTALINGEN:
				this.GetWoorden(false);
				break;
			case MET_AFLEIDINGEN:
				this.GetWoorden(true);
				break;
			case ALLES:
				this.GetWoorden(true);
			case VERVOEGINGEN:
				this.GetVervoegingen();
				break;
			default:
				break;
			}

			this.random = new Random(System.currentTimeMillis());
		} catch (Exception e) {
			throw e;
		}

		return this.aantalIds;
	}

	public TestData GetOpdracht() throws Exception {
		int index;
		int pogingen = 0;
		TestData testData;

		do {
			index = this.random.nextInt(this.aantalIds);
			pogingen++;

			if (pogingen >= this.aantalIds) {
				throw new Exception("Geen nieuwe vragen gevonden");
			}
		} while (this.historiek.contains(index));

		testData = this.dataArray.get(index);
		this.historiek.add(index);

		return testData;
	}

	private void GetWoorden(boolean aMetAfleidingen) throws Exception {
		File xmlFile;
		DocumentBuilderFactory dbFactory;
		DocumentBuilder dBuilder;
		Document doc;
		NodeList nodeList;
		Node node;
		Element element;
		int max;
		String vraag, antwoord;
		TestData testData;
		String nederlands, italiaans, meervoud, vrouwEnkelvoud, vrouwMeervoud;

		try {
			xmlFile = new File(this.dataPath, "dbWoord.xml");
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			nodeList = doc.getElementsByTagName("Woord");
			max = nodeList.getLength();

			for (int temp = 0; temp < max; temp++) {
				node = nodeList.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) node;
					nederlands = element.getElementsByTagName("Nederlands").item(0).getTextContent();
					italiaans = element.getElementsByTagName("Italiaans").item(0).getTextContent();

					vraag = "Vertaal: " + nederlands;
					antwoord = italiaans;
					testData = new TestData(vraag, antwoord);
					this.dataArray.add(testData);
					this.aantalIds++;

					if (aMetAfleidingen == true) {
						meervoud = element.getElementsByTagName("Meervoud").item(0).getTextContent();
						vrouwEnkelvoud = element.getElementsByTagName("VrouwEnkelvoud").item(0).getTextContent();
						vrouwMeervoud = element.getElementsByTagName("VrouwMeervoud").item(0).getTextContent();

						if (meervoud.trim() != "") {
							vraag = "Geef het meervoud van: " + nederlands;
							antwoord = meervoud;
							testData = new TestData(vraag, antwoord);
							this.dataArray.add(testData);
							this.aantalIds++;
						}

						if (vrouwEnkelvoud.trim() != "") {
							vraag = "Geef het vrouwelijk van: " + nederlands;
							antwoord = vrouwEnkelvoud;
							testData = new TestData(vraag, antwoord);
							this.dataArray.add(testData);
							this.aantalIds++;
						}

						if (vrouwMeervoud.trim() != "") {
							vraag = "Geef het vrouwelijk meervoud van: " + nederlands;
							antwoord = vrouwMeervoud;
							testData = new TestData(vraag, antwoord);
							this.dataArray.add(testData);
							this.aantalIds++;
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void GetVervoegingen() throws Exception {
		Hashtable<Long, Element> infinitieven;
		Hashtable<Long, Element> tijdPersonen;
		File xmlFile;
		DocumentBuilderFactory dbFactory;
		DocumentBuilder dBuilder;
		Document doc;
		NodeList nodeList;
		Node node;
		Element element, tijdPersoonElement, woordElement;
		int max;
		String vraag, antwoord;
		TestData testData;
		String nederlands, italiaans, tijd, persoon, infinitief;
		long tijdPersoonId, woordId;

		try {
			tijdPersonen = new Hashtable<Long, Element>();
			this.GetTijdPersonen(tijdPersonen);
			infinitieven = new Hashtable<Long, Element>();
			this.GetInfinitieven(infinitieven);
			xmlFile = new File(this.dataPath, "dbVervoeging.xml");
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			nodeList = doc.getElementsByTagName("Vervoeging");
			max = nodeList.getLength();

			for (int temp = 0; temp < max; temp++) {
				node = nodeList.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) node;
					nederlands = element.getElementsByTagName("Nederlands").item(0).getTextContent();
					italiaans = element.getElementsByTagName("Italiaans").item(0).getTextContent();

					if (italiaans.trim() != "") {
						tijdPersoonId = Long.parseLong(element.getElementsByTagName("TijdPersoonId").item(0)
								.getTextContent());
						tijdPersoonElement = tijdPersonen.get(tijdPersoonId);
						tijd = tijdPersoonElement.getElementsByTagName("Tijd").item(0).getTextContent();
						persoon = tijdPersoonElement.getElementsByTagName("Persoon").item(0).getTextContent();
						woordId = Long.parseLong(element.getElementsByTagName("WoordId").item(0).getTextContent());
						woordElement = infinitieven.get(woordId);
						infinitief = woordElement.getElementsByTagName("Italiaans").item(0).getTextContent();

						vraag = "Vervoeg: " + tijd + (persoon.equals("-") ? "" : (" - " + persoon)) + " - "
								+ infinitief + (nederlands.equals("") ? "" : " (" + nederlands + ")");
						antwoord = italiaans;
						testData = new TestData(vraag, antwoord);
						this.dataArray.add(testData);
						this.aantalIds++;
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void GetTijdPersonen(Hashtable<Long, Element> aTijdPersonen) throws Exception {
		File xmlFile;
		DocumentBuilderFactory dbFactory;
		DocumentBuilder dBuilder;
		Document doc;
		NodeList nodeList;
		Node node;
		Element element;
		int max;
		long tijdPersoonId;

		try {
			xmlFile = new File(this.dataPath, "dbTijdPersoon.xml");
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			nodeList = doc.getElementsByTagName("TijdPersoon");
			max = nodeList.getLength();

			for (int temp = 0; temp < max; temp++) {
				node = nodeList.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) node;
					tijdPersoonId = Long.parseLong(element.getElementsByTagName("TijdPersoonId").item(0)
							.getTextContent());
					aTijdPersonen.put(tijdPersoonId, element);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void GetInfinitieven(Hashtable<Long, Element> aInfinitieven) throws Exception {
		File xmlFile;
		DocumentBuilderFactory dbFactory;
		DocumentBuilder dBuilder;
		Document doc;
		NodeList nodeList;
		Node node;
		Element element;
		int max;
		long woordId;

		try {
			xmlFile = new File(this.dataPath, "dbWoord.xml");
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			nodeList = doc.getElementsByTagName("Woord");
			max = nodeList.getLength();

			for (int temp = 0; temp < max; temp++) {
				node = nodeList.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) node;
					woordId = Long.parseLong(element.getElementsByTagName("WoordId").item(0).getTextContent());
					aInfinitieven.put(woordId, element);

				}
			}
		} catch (Exception e) {
			throw e;
		}
	}
}

package com.giovanni.testapp.utils;

import android.graphics.Bitmap;

public class StatisticItem {

	private String punteggio;
	private String dataPunteggio;
	private String categoriaUtente;
	private Bitmap imagePunteggio;
	
	public StatisticItem(String punteggio, String dataPunteggio, String categoriaUtente/*,Bitmap imagePunteggio*/){
		
		super();
		this.punteggio = punteggio;
		this.dataPunteggio = dataPunteggio;
		this.categoriaUtente = categoriaUtente;
		//this.imagePunteggio = imagePunteggio;
	}

	public String getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(String punteggio) {
		this.punteggio = punteggio;
	}

	public String getDataPunteggio() {
		return dataPunteggio;
	}

	public void setDataPunteggio(String dataPunteggio) {
		this.dataPunteggio = dataPunteggio;
	}

	public String getCategoriaUtente() {
		return categoriaUtente;
	}

	public void setCategoriaUtente(String categoriaUtente) {
		this.categoriaUtente = categoriaUtente;
	}

	public Bitmap getImagePunteggio() {
		return imagePunteggio;
	}

	public void setImagePunteggio(Bitmap imagePunteggio) {
		this.imagePunteggio = imagePunteggio;
	}

}

package rva.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Filijala implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "FILIJALA_SEQ_GENERATOR", sequenceName = "FILIJALA_SEQ", 
	allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILIJALA_SEQ_GENERATOR")
	private int id;
	private String adresa;
	private int brojPultova;
	private boolean posedujeSef;
	
	@ManyToOne
	@JoinColumn(name = "banka")
	private Banka banka;
	
	@OneToMany(mappedBy = "filijala", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Usluga> usluge;
	
	public Filijala() {
		
	}
	
	public Filijala(int id, String adresa, int brojPultova, boolean posedujeSef) {
		this.id=id;
		this.adresa=adresa;
		this.brojPultova=brojPultova;
		this.posedujeSef = posedujeSef;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public int getBrojPultova() {
		return brojPultova;
	}

	public void setBrojPultova(int brojPultova) {
		this.brojPultova = brojPultova;
	}

	public boolean isPosedujeSef() {
		return posedujeSef;
	}

	public void setPosedujeSef(boolean posedujeSef) {
		this.posedujeSef = posedujeSef;
	}

	public Banka getBanka() {
		return banka;
	}

	public void setBanka(Banka banka) {
		this.banka = banka;
	}

	public List<Usluga> getUsluge() {
		return usluge;
	}

	public void setUsluge(List<Usluga> usluge) {
		this.usluge = usluge;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}

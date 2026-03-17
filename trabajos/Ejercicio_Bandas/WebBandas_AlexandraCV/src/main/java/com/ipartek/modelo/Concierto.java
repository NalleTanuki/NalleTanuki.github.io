package com.ipartek.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="conciertos")
public class Concierto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	//@Column(name="fecha", columnDefinition = "Date")
	//private String fecha;
	@DateTimeFormat(pattern="yyyy-MM-dd") //sino no pone la fecha cuando modificas conci
	@Column(name="fecha", nullable=false)
	private LocalDate fecha;
	
	@Column(name = "hora", nullable = false)
	private LocalTime hora;
	
	@Column(name="precio", nullable=false, precision = 10, scale=2)
	private BigDecimal precio;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Pais pais;
	
	@ManyToMany
	@JoinTable(name="conciertos_listabandas",
			   joinColumns = @JoinColumn(name="concierto_id"),
			   inverseJoinColumns = @JoinColumn(name="banda_id"))
	private List<Banda> listaBandas = new ArrayList<>();

	
	public Concierto() {
		super();
		this.id = 0;
		this.fecha = LocalDate.now();
		this.hora = LocalTime.now();
		this.precio = BigDecimal.ZERO;
		this.listaBandas = new ArrayList<Banda>();
	}
	
	public Concierto(int id, LocalDate fecha, LocalTime hora, BigDecimal precio, Pais pais, List<Banda> listaBandas) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.hora = hora;
		this.precio = precio;
		this.pais = pais;
		this.listaBandas = listaBandas;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}
	
	public List<Banda> getListaBandas(){
		return listaBandas;
	}
	
	public void setListaBandas(List<Banda> listaBandas) {
		this.listaBandas = listaBandas;
	}

	

	@Override
	public String toString() {
		return "Concierto [id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", precio=" + precio + ", pais=" + pais + ", listaBandas=" + listaBandas + "]";
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(fecha, hora, id, listaBandas, pais, precio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Concierto other = (Concierto) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(hora, other.hora) && id == other.id
				&& Objects.equals(listaBandas, other.listaBandas) && Objects.equals(pais, other.pais)
				&& Objects.equals(precio, other.precio);
	}	
}

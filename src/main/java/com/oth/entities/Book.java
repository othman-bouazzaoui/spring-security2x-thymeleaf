package com.oth.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * @author Othman BOUAZZAOUI
 */
@Entity
@Table(name = "BOOK")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Book implements Serializable {
	private static final long serialVersionUID = -6664958488217978342L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@JsonIgnore
	@Column(name = "book_id")
	private Long id;
	@Column(name = "TITLE", nullable = false)
	private String name;
	//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // ce code pour LAZY
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Author author;

	@Override
	public String toString() {
		return "Book [ name=" + name + ", author=" + author + "]";
	}
}

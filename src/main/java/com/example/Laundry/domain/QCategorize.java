// Domain: com.example.Laundry.domain.QCategorize.java
package com.example.Laundry.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "qcategorize")
public class QCategorize {

    @Id
    @Column(length = 20)
    private String category;

    public QCategorize() { }

    public QCategorize(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

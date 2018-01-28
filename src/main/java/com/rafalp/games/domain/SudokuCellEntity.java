package com.rafalp.games.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SudokuCellEntity.
 */
@Entity
@Table(name = "sudoku_cell_entity")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SudokuCellEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "jhi_value")
    private Integer value;

    @Column(name = "solution")
    private Integer solution;

    @Column(name = "row_number")
    private Integer rowNumber;

    @Column(name = "column_number")
    private Integer columnNumber;

    @Column(name = "draft_number")
    private Boolean draftNumber;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public SudokuCellEntity userId(Long userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getValue() {
        return value;
    }

    public SudokuCellEntity value(Integer value) {
        this.value = value;
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getSolution() {
        return solution;
    }

    public SudokuCellEntity solution(Integer solution) {
        this.solution = solution;
        return this;
    }

    public void setSolution(Integer solution) {
        this.solution = solution;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public SudokuCellEntity rowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
        return this;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getColumnNumber() {
        return columnNumber;
    }

    public SudokuCellEntity columnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
        return this;
    }

    public void setColumnNumber(Integer columnNumber) {
        this.columnNumber = columnNumber;
    }

    public Boolean isDraftNumber() {
        return draftNumber;
    }

    public SudokuCellEntity draftNumber(Boolean draftNumber) {
        this.draftNumber = draftNumber;
        return this;
    }

    public void setDraftNumber(Boolean draftNumber) {
        this.draftNumber = draftNumber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuCellEntity sudokuCellEntity = (SudokuCellEntity) o;
        if (sudokuCellEntity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sudokuCellEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SudokuCellEntity{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", value=" + getValue() +
            ", solution=" + getSolution() +
            ", rowNumber=" + getRowNumber() +
            ", columnNumber=" + getColumnNumber() +
            ", draftNumber='" + isDraftNumber() + "'" +
            "}";
    }
}

import {Component, Input, OnInit} from '@angular/core';
import {SudokuGenResponse} from '../sudoku-game.component';
import {SudokuGameRequestsService} from '../sudoku-game-requests.service';
import {Subject} from 'rxjs/Subject';

@Component({
    selector: 'jhi-sudoku-board',
    templateUrl: './sudoku-board.component.html',
    styleUrls: ['./sudoku-board.component.css']
})
export class SudokuBoardComponent implements OnInit {

    board: Cell[][];
    draftBoard: Cell[][];
    isDone = false;

    @Input() solutionVisibleSubject: Subject<boolean>;
    @Input() newBoardSubject: Subject<SudokuGenResponse>;

    constructor(private sudokuRequest: SudokuGameRequestsService) {
    }

    ngOnInit() {
        this.solutionVisibleSubject.subscribe((event) => this.showSolution(event));
        this.newBoardSubject.subscribe((event) => this.createNewSudoku(event));
    }

    ngOnDestroy() {
        //this.solutionVisibleSubject.unsubscribe();
        //this.newBoardSubject.unsubscribe();
    }

    showSolution(visible: boolean): void {
        for (let i = 0; i < this.board.length; i++) {
            for (let j = 0; j < this.board.length; j++) {
                this.board[i][j].setSolutionVisibility(visible);
            }
        }
    }

    createNewSudoku(sudoku: SudokuGenResponse): void {
        this.setDraftAndBoard(sudoku.board);
        this.isDone = false;
    }

    setDraftAndBoard(board: Cell[][]) {
        this.board = [];
        for (let i = 0; i < 9; i++) {
            this.board[i] = [];
            for (let j = 0; j < 9; j++) {
                this.board[i][j] = new Cell(board[i][j].value, board[i][j].solution, board[i][j].x, board[i][j].y, board[i][j].draftNumber);
            }
        }

        this.draftBoard = [];
        for (let i = 0; i < 9; i++) {
            this.draftBoard[i] = [];
            for (let j = 0; j < 9; j++) {
                this.draftBoard[i][j] = new Cell(board[i][j].value, board[i][j].solution, board[i][j].x, board[i][j].y, board[i][j].draftNumber);
            }
        }
    }

    fillSudokuWithDummyData(): void {
        this.board = [];
        for (let i = 0; i < 9; i++) {
            this.board[i] = [];
            for (let j = 0; j < 9; j++) {
                this.board[i][j] = new Cell(0, 0, i, j, true);
            }
        }
    }

    init(): void {

    }

    valueEntered(event, i, j) {
        const charCode = (event.which) ? event.which : event.keyCode;
        if (charCode > 48 && charCode < 58) {
            this.setCellValue(Number(String.fromCharCode(charCode)), i, j);
            this.saveChangedSudoku();
            this.checkIsSudokuDone();
        } else if (charCode > 96 && charCode < 106) {
            this.setCellValue(Number(String.fromCharCode(charCode - 48)), i, j);
            this.saveChangedSudoku();
            this.checkIsSudokuDone();
        } else if (charCode === 8 || charCode === 46 || charCode === 32) {
            this.setCellValue(0, i, j);
            this.saveChangedSudoku();
        } else if (charCode === 9 || charCode === 38 || charCode === 39) {
            return true;
        }
        return false;
    }

    saveChangedSudoku() {
        const sudokuToSave: SudokuGenResponse = {board: this.board, userId: 5};
        this.sudokuRequest.saveChangedSudoku(sudokuToSave);
    }

    setCellValue(value, i, j): void {
        console.log(value);
        this.board[i][j].setValue(value);
    }

    checkIsSudokuDone(): void {
        if (!this.isDone) {
            let done = true;
            for (let i = 0; i < this.board.length; i++) {
                for (let j = 0; j < this.board.length; j++) {
                    if (this.board[i][j].value !== this.board[i][j].solution) {
                        done = false;
                        break;
                    }
                }
                if (!done) {
                    break;
                }
            }
            if (done) {
                this.isDone = true;
                alert('Congratulation! You have solved Sudoku!');
            }
        }
    }
}

export class Cell {
    value: number;
    solution: number;
    x: number;
    y: number;
    draftNumber: boolean;
    solutionVisible: boolean;

    constructor(value, solution, x, y, isDraftNumber) {
        this.value = value;
        this.solution = solution;
        this.x = x;
        this.y = y;
        this.draftNumber = isDraftNumber;
    }

    setSolutionVisibility(visible: boolean): void {
        if (this.value !== this.solution) {
            this.solutionVisible = visible;
        }
    }

    public setValue(value): void {
        this.value = value;
    }

    public getValueAsString(): string {
        return this.value === 0 ? '' : this.value.toString();
    }

    public getValueOrSolution(): string {
        if (this.solutionVisible) {
            return this.solution.toString();
        } else {
            return this.value === 0 ? '' : this.value.toString();
        }
    }

    public isEditable(): boolean {
        return (!this.draftNumber && !this.solutionVisible);
    }

    getColorStyle(): String {
        if (this.draftNumber) {
            return 'draftCell';
        } else if (this.value === this.solution) {
            return 'okCell';
        } else if (this.solutionVisible === true) {
            return 'solutionCell';
        } else {
            return 'wrongCell';
        }
    }

    getBorderStyle(): string {
        let style = '';
        if ((this.y + 1) % 3 === 0 && (this.y + 1) !== 9) {
            style += ' right-border-cell ';
        }
        if ((this.x + 1) % 3 === 0 && (this.x + 1) !== 9) {
            style += ' bottom-border-cell ';
        }
        return style;
    }
}

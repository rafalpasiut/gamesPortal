import { BaseEntity } from './../../shared';

export class SudokuCellEntity implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public value?: number,
        public solution?: number,
        public rowNumber?: number,
        public columnNumber?: number,
        public draftNumber?: boolean,
    ) {
        this.draftNumber = false;
    }
}

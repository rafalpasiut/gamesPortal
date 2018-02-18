import { BaseEntity } from './../../shared';

export class RPSGames implements BaseEntity {
    constructor(
        public id?: number,
        public player1?: string,
        public player1Champion?: string,
        public player1Count?: number,
        public player1IsPlayed?: boolean,
        public player2?: string,
        public player2Champion?: string,
        public player2Count?: number,
        public player2IsPlayed?: boolean,
        public isAI?: boolean,
        public gameStartTime?: any,
        public lastActionTime?: any,
        public isGameFinished?: boolean,
        public isRoundFinished?: boolean,
    ) {
        this.player1IsPlayed = false;
        this.player2IsPlayed = false;
        this.isAI = false;
        this.isGameFinished = false;
        this.isRoundFinished = false;
    }
}

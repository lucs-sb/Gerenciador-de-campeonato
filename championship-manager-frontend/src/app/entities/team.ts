import { Player } from "./player";

export interface Team{
    id: number;
    shield_img: string;
    name: string;
    abbreviation: string;
    players: Player[];
}
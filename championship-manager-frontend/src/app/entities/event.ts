import { Match } from "./match";
import { Player } from "./player";

export interface Event{
    id: number;
    match: Match;
    player: Player;
    description: string;
    time: string;
    value: number;
}
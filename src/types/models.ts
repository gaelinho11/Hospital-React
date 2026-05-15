export interface Patient{
    nif: string;
    fullName: string;
    age: number;
    diagnosis: string;
    monitored: boolean;
    urgency: number;
    room?: number;
}

export interface Room{
    id: number;
    monitored: boolean;
    roomType: string;
}

export interface log{
    id: number;
    timestamp: string;
    action: string;
    detail: string;
}

export interface RoomDTO{
    numero: number;
    tipus: string;
    monitoritzada: boolean;
    lliure: boolean;
    pacient?: Patient;
}
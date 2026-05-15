import type { Room, Patient, RoomDTO, log} from "../types/models";

const BASE_URL = "http://localhost:8080/PWP3VillanuevaG/api";

// ========================
//  USERS
// ========================

export async function crearRoom(room: Room) {
  const response = await fetch(`${BASE_URL}/rooms`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(room),
  });

  if (response.status === 201) {
    return await response.json();
  } else if (response.status === 409) {
    const msg = await response.json();
    throw new Error(msg);
  } else {
    throw new Error("Error inesperat del servidor.");
  }
}
export async function llistarRooms(): Promise<Room[]> {
  const response = await fetch(`${BASE_URL}/rooms`);

  if (response.ok) {
    return await response.json();
  } else {
    throw new Error("Error obtenint la llista d'usuaris.");
  }
}

export async function crearPatient(patient: Patient) {
  const response = await fetch(`${BASE_URL}/patients`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(patient),
  });

  if (response.status === 201) {
    return await response.json();
  } else if (response.status === 409) {
    const msg = await response.json();
    throw new Error(msg);
  } else {
    throw new Error("Error inesperat del servidor.");
  }
}
export async function roomsState(): Promise<RoomDTO[]> {
  const response = await fetch(`${BASE_URL}/rooms`);

  if (response.ok) {
    return await response.json();
  } else {
    throw new Error("Error obtenint els estats de les habitacions.");
  }
}

export async function llistarPatientsLliures(): Promise<Patient[]> {
  const response = await fetch(`${BASE_URL}/patients`);

  if (response.ok) {
    return await response.json();
  } else {
    throw new Error("Error obtenint la llista de pacients.");
  }
}
export async function llistarPatientsOcupats(): Promise<Patient[]> {
  const response = await fetch(`${BASE_URL}/patients?a=a`); //passo la a per diferenciar si he de portar pacients lliures o ocupats
  if (response.ok) {
    return await response.json();
  } else {
    throw new Error("Error obtenint la llista de pacients.");
  }
}
export async function assignarHabitació(nif : string): Promise<Room> {
  const response = await fetch(`${BASE_URL}/patients/${nif}`, {
    method: "PUT",
  });

  if(response.ok){
    return await response.json();
  } else if (response.status === 409) { //409 he cualsevol dels cops que throwejo excepcions
    const msg = await response.json();
    throw new Error(msg);
  } else {
    throw new Error("Error inesperat del servidor.");
  }
}
export async function donarAlta(nif : string) {
  const response = await fetch(`${BASE_URL}/patients/${nif}`, {
    method: "DELETE",
  });
    if (!response.ok) {
      throw new Error("Error al donar d'alta");
    }
}
export async function llistarHistorial(nif: string): Promise<log[]> {
  const response = await fetch(`${BASE_URL}/logs?a=${nif}`);

  if (response.ok) {
    return await response.json();
  }else if (response.status === 409){
    const msg = await response.json();
    throw new Error(msg)
  }else {
    throw new Error("Error obtenint els estats de les habitacions.");
  }
  
}
/*
export async function llistarTasquesPerUsuari(nif: string): Promise<TaskResponse[]> {
    const response = await fetch(`${BASE_URL}/tasks?user=${nif}`)
    if (response.ok) {
        return await response.json()
    } else if (response.status === 404) {
        const msg = await response.json()
        throw new Error(msg)
    } else {
        throw new Error("Error obtenint les tasques.")
    }
}
export async function marcarTascaCompletada(id: number): Promise<void> {
  const response = await fetch(`${BASE_URL}/tasks/${id}`, {
    method: "PUT",
  });

  if (!response.ok) {
    throw new Error("Error marcant la tasca com a completada.");
  }
}

export async function eliminarTasca(id: number): Promise<void> {
  const response = await fetch(`${BASE_URL}/tasks/${id}`, {
    method: "DELETE",
  });

  if (!response.ok) {
    throw new Error("Error eliminant la tasca.");
  }
}

    */

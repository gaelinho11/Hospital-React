import { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import type { RoomDTO } from "../types/models";
import { roomsState } from "../services/api";

function RoomStatePage() {
    const navigate = useNavigate();
    const [rooms, setRooms] = useState<RoomDTO[]>([]);
    const [error, setError] = useState("");

    useEffect(() => {
    roomsState()
        .then((data) => setRooms(data))
        .catch((err) => setError(err.message));
    }, []);

    return (

        <div className="container mt-5">
            <h2 className="display-6 fw-bold mb-4" style={{ color: '#0d6efd' }}>
            <i className="bi bi-bed me-2"></i>
                🛏️Estat de les Habitacions
            </h2>

            {error && <div className="alert alert-danger mt-3">{error}</div>}

            {rooms.length === 0 && !error ? (
            <div className="alert alert-info mt-3">
                <i className="bi bi-info-circle me-2"></i>
                No hi ha habitacions registrades
            </div>
            ) : (
            <div className="table-responsive mt-3">
                <table className="table align-middle">
                <thead className="table-light">
                    <tr>
                    <th>Número</th>
                    <th>Tipus</th>
                    <th>Monitoritzada</th>
                    <th>Estat</th>
                    <th>Pacient</th>
                    </tr>
                </thead>
                <tbody>
                    {rooms.map((room) => (
                    <tr 
                        key={room.numero} 
                        className={room.lliure ? "table-success" : "table-danger"}
                        style={{ opacity: 0.9 }}
                    >
                        <td className="fw-bold">{room.numero}</td>
                        <td>{room.tipus}</td>
                        <td>{room.monitoritzada ? "✅" : "❌"}</td>
                        <td>
                        <span className={`badge rounded-pill ${room.lliure ? "bg-success" : "bg-danger"}`}>
                            {room.lliure ? "Lliure" : "Ocupada"}
                        </span>
                        </td>
                        <td>
                        {room.pacient ? (
                            `${room.pacient.nif} - ${room.pacient.fullName}`
                        ) : (
                            "-"
                        )}
                        </td>
                    </tr>
                    ))}
                </tbody>
                </table>
            </div>
            )}

            <button className="btn btn-secondary mt-3" onClick={() => navigate("/")}>
                Tornar
            </button>
        </div>
    );
}

export default RoomStatePage;
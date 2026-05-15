import { useState } from "react";
import { useNavigate } from "react-router";
import { llistarHistorial } from "../services/api";
import type { log } from "../types/models"; // Assegura't que el tipus es digui Log o log

function HistorialPage() {
  const navigate = useNavigate();
  const [nifInput, setNifInput] = useState("");
  const [logs, setLogs] = useState<log[]>([]);
  const [searched, setSearched] = useState(false);
  const [error, setError] = useState("");

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setSearched(false);

    if (!nifInput.trim()) {
      setError("Si us plau, indica un DNI vàlid.");
      return;
    }

    try {
      const data = await llistarHistorial(nifInput);
      setLogs(data);
      setSearched(true);
    } catch (err: any) {
      setError(err.message);
      setLogs([]);
    }
  };

  // Funció per posar el color del badge segons l'acció
  const getBadgeClass = (accio: string) => {
    switch (accio) {
      case "INGR_PAC": return "bg-warning text-dark";
      case "ALTA_MED": return "bg-success";
      case "ALTA_PAC": return "bg-primary";
      default: return "bg-secondary";
    }
  };

  return (
    <div className="container mt-5">
      <button className="btn btn-outline-secondary mb-3 btn-sm" onClick={() => navigate("/")}>
        ← Tornar a l'inici
      </button>

      <div className="card shadow-sm border-0">
        {/* Capçalera blau cel com a la foto */}
        <div className="card-header py-3" style={{ backgroundColor: "#17d1ff", color: "white" }}>
          <h5 className="mb-0 fw-bold">
            <i className="bi bi-search me-2"></i>
            Fitxa Pacient - Historial
          </h5>
        </div>

        <div className="card-body">
          <form onSubmit={handleSearch} className="d-flex gap-2 mb-4">
            <input
              type="text"
              className="form-control"
              placeholder="Escriu el DNI del pacient..."
              value={nifInput}
              onChange={(e) => setNifInput(e.target.value)}
            />
            <button type="submit" className="btn btn-info text-white fw-bold">
              Consultar
            </button>
          </form>

          {error && <div className="alert alert-danger border-0">{error}</div>}

          {searched && logs.length === 0 && !error && (
            <div className="alert alert-info border-0">
              <i className="bi bi-info-circle me-2"></i>
              Aquest DNI no té registres a l'historial.
            </div>
          )}

          {logs.length > 0 && (
            <div className="table-responsive">
              <table className="table table-hover align-middle">
                <thead className="table-light">
                  <tr>
                    <th>ID</th>
                    <th>Data i Hora</th>
                    <th>Acció</th>
                    <th>Detall</th>
                  </tr>
                </thead>
                <tbody>
                  {logs.map((log) => (
                    <tr key={log.id}>
                      <td className="text-muted">{log.id}</td>
                      <td>{log.timestamp}</td>
                      <td>
                        <span className={`badge ${getBadgeClass(log.action)}`}>
                          {log.action}
                        </span>
                      </td>
                      <td className="small text-secondary">{log.detail}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default HistorialPage;
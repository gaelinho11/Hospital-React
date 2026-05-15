import { Link } from 'react-router';

function Home() {
  return (
    <div className="container mt-5">
      <div className="card shadow border-0 p-5 mb-5 bg-white rounded">
        <div className="row align-items-center">
          <div className="col-12">
            <h1 className="display-5 fw-bold mb-3">
              🏥 <span className="text-primary">Hospital</span> Salut Global
            </h1>
            <p className="lead text-secondary">
              Benvinguts a la terminal de control de l'<strong>Hospital Salut Global</strong>. 
              Mentre la logística col·lapsa a passadissos, aquí domina la precisió digital.
            </p>
            <hr className="my-4" />
            <p className="fst-italic text-muted small mb-4">
              "Curant pacients des de 2026. Reiniciant el sistema des de fa cinc minuts."
            </p>
            <div className="d-flex gap-3">
              <Link to="/rooms/new" className="btn btn-outline-primary px-4 py-2">
                🛏️ Alta d'Habitació
              </Link>
              <Link to="/patients/new" className="btn btn-primary px-4 py-2">
                👩‍⚕️ Registre de Pacients
              </Link>
            </div>
          </div>
        </div>
      </div>


      <div className="row g-4 text-center">
        
        <div className="col-md-3">
          <div className="card h-100 border-primary border-3 shadow-sm p-3">
            <div className="card-body d-flex flex-column justify-content-between">
              <h5 className="card-title fw-bold text-primary mb-3">Estat de l'Hospital</h5>
              <p className="card-text text-muted small">Ocupació de plantes en temps real.</p>
              <Link to="/rooms/status" className="btn btn-outline-primary rounded-pill mt-3">
                Veure mapa
              </Link>
            </div>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card h-100 border-warning border-3 shadow-sm p-3">
            <div className="card-body d-flex flex-column justify-content-between">
              <h5 className="card-title fw-bold text-warning mb-3">Assignació Ràpida</h5>
              <p className="card-text text-muted small">Ingrés intel·ligent a habitacions lliures.</p>
              <Link to="/patients/asignation" className="btn btn-outline-warning rounded-pill mt-3">
                Ingressar ara
              </Link>
            </div>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card h-100 border-success border-3 shadow-sm p-3">
            <div className="card-body d-flex flex-column justify-content-between">
              <h5 className="card-title fw-bold text-success mb-3">Alta Mèdica</h5>
              <p className="card-text text-muted small">Alliberar habitació i finalitzar estada.</p>
              <Link to="/patients/alta" className="btn btn-outline-success rounded-pill mt-3">
                Tramitar alta
              </Link>
            </div>
          </div>
        </div>

        <div className="col-md-3">
          <div className="card h-100 border-info border-3 shadow-sm p-3">
            <div className="card-body d-flex flex-column justify-content-between">
              <h5 className="card-title fw-bold text-info mb-3">Fitxa Pacient</h5>
              <p className="card-text text-muted small">Historial complet i traces de logs.</p>
              <Link to="/patients/log" className="btn btn-outline-info rounded-pill mt-3">
                Consultar
              </Link>
            </div>
          </div>
        </div>

      </div>
    </div>
  );
}

export default Home;
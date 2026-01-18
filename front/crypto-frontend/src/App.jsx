import { useEffect, useState } from 'react'
import './App.css'

function App() {
  const [market, setMarket] = useState([]);
  const [favorites, setFavorites] = useState([]);
  const [targetPrice, setTargetPrice] = useState({});
  const [loading, setLoading] = useState(false);

  const API_BASE = "http://localhost:8080/api/crypto";

  useEffect(() => {
    refreshData();
  }, []);

  const refreshData = () => {
    setLoading(true);

    fetch(`${API_BASE}/getAlets`)
      .then(res => {
        if (!res.ok) throw new Error("Rate Limit");
        return res.json();
      })
      .then(data => {
        setMarket(data);
        const favs = data.filter(coin => coin.alertMessage !== "Sin alerta");
        setFavorites(favs);
      })
      .catch(err => alert("Consumiste mucho esta api. Espera 1 minuto."))
      .finally(() => setLoading(false));
  };

  const handleSave = (coin) => {
    const price = targetPrice[coin.id];
    if (!price) return alert("Ingresa un precio objetivo");

    const favoriteData = {
      id: coin.id,
      name: coin.name,
      targetPrice: parseFloat(price)
    };

    fetch(`${API_BASE}/postNewFavorite`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(favoriteData)
    })
      .then(res => {
        if (res.ok) {
          alert("¡Guardado con éxito!");
          refreshData();
        }
      });
  };

  return (
    <div className="min-vh-100 pb-5 card">
      <nav className="navbar navbar-light bg-light mb-4 shadow">
        <div className="container">
          <span className="navbar-brand font-digital">CRYPTO ALERT SYSTEM</span>
        </div>
      </nav>

      <div className="container">
        <div className="d-flex justify-content-between align-items-center mb-4 card-header font-page-green">
          <div className="display-7 font-page-purple font-digital">Mercado en Tiempo Real</div>
          <button
            className="btn ltr-white btn-purple shadow-sm fw-bold"
            onClick={refreshData}
            disabled={loading}
          >
            {loading ? (
              <span className="spinner-border spinner-border-sm me-2"></span>
            ) : ''} Actualizar Precios
          </button>
        </div>
        <div className="card-body font-page-green border-0 mb-4">
          <div className="table-responsive">
            <table className="table  table-success table-striped table-hover table-bordered align-middle">
              <thead className="table-purple">
                <tr>
                  <th>Moneda</th>
                  <th>Precio Actual</th>
                  <th>Estado / Alerta</th>
                  <th>Precio Objetivo</th>
                  <th className="text-center">Acción</th>
                </tr>
              </thead>
              <tbody>
                {market.map(coin => (
                  <tr key={coin.id}>
                    <td className="fw-bold text-secondary">{coin.name}</td>
                    <td className="fw-bold">${coin.current_price?.toLocaleString()}</td>
                    <td>
                      <span className={`badge p-2 ${coin.alertMessage?.includes("OFERTA") ? 'bg-success' : 'bg-warning text-dark'}`}>
                        {coin.alertMessage}
                      </span>
                    </td>
                    <td>
                      <div className="input-group input-group-sm">
                        <span className="input-group-text">$</span>
                        <input
                          type="number"
                          className="form-control"
                          placeholder="Objetivo"
                          onChange={(e) => setTargetPrice({ ...targetPrice, [coin.id]: e.target.value })}
                        />
                      </div>
                    </td>
                    <td className="text-center">
                      <button className="btn btn-primary  btn-sm rounded-pill px-3 btn-green" onClick={() => handleSave(coin)}>
                        Guardar
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
        <div className="card-footer font-page-green">
          <div className="display-7 font-page-purple font-digital my-2">Mis Favoritos</div>
          <div className="row g-3">
            {favorites.length === 0 ? (
              <div className="col-12">
                <div className="alert alert-light text-center border">No tienes favoritos guardados aún.</div>
              </div>
            ) : (
              favorites.map(fav => (
                <div key={fav.id} className="col-12 col-sm-6 col-md-4 col-lg-3">
                  <div className="card h-100 card-favorite shadow-sm font-page-purple border-0">
                    <div className="card-body py-3">
                      <h6 className="card-title fw-bold text-dark mb-1">{fav.name}</h6>
                      <p className="card-text mb-0 font-digital" style={{ fontSize: '0.65rem' }}>
                        OBJ: ${fav.targetPrice}
                      </p>
                    </div>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
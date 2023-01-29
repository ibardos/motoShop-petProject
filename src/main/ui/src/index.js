import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

// Page imports
import Layout from "./pages/Layout";
import Home from "./pages/Home";
import Manufacturers from "./pages/Manufacturers";
import Motorcycles from "./pages/motorcycles/Motorcycles";
import MotorcycleModel from "./pages/motorcycles/MotorcycleModel";
import MotorcycleStock from "./pages/motorcycles/MotorcycleStock";
import NoPage from "./pages/NoPage";

// Style imports
import "./index.css";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Layout />}>
                    <Route index element={<Home />} />
                    <Route path="manufacturers" element={<Manufacturers />} />
                    <Route path="motorcycles">
                        <Route index element={<Motorcycles />} />
                        <Route path="model" element={<MotorcycleModel />} />
                        <Route path="stock" element={<MotorcycleStock />} />
                    </Route>
                    <Route path="*" element={<NoPage />} />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(<App />);
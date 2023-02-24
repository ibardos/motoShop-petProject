import {StrictMode} from "react";

import {BrowserRouter, Route, Routes} from "react-router-dom";

import Layout from "./pages/Layout";
import Home from "./pages/Home";
import Manufacturers from "./pages/Manufacturers";
import Motorcycles from "./pages/motorcycles/Motorcycles";
import MotorcycleModel from "./pages/motorcycles/MotorcycleModel";
import MotorcycleStock from "./pages/motorcycles/MotorcycleStock";
import NoPage from "./pages/NoPage";


const App = () => {
    return (
        <StrictMode>
            <BrowserRouter>
                <Routes>
                    <Route path="/" element={<Layout/>}>
                        <Route index element={<Home/>}/>
                        <Route path="manufacturers" element={<Manufacturers/>}/>
                        <Route path="motorcycles">
                            <Route index element={<Motorcycles/>}/>
                            <Route path="model" element={<MotorcycleModel/>}/>
                            <Route path="stock" element={<MotorcycleStock/>}/>
                        </Route>
                        <Route path="*" element={<NoPage/>}/>
                    </Route>
                </Routes>
            </BrowserRouter>
        </StrictMode>
    );
}

export default App;
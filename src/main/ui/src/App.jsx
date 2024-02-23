import {StrictMode} from "react";

import {BrowserRouter, Route, Routes} from "react-router-dom";

import Layout from "./pages/Layout";
import Service from "./pages/service/Service";
import Home from "./pages/Home";
import Manufacturer from "./pages/service/Manufacturer";
import Motorcycle from "./pages/service/motorcycle/Motorcycle";
import MotorcycleModel from "./pages/service/motorcycle/MotorcycleModel";
import MotorcycleStock from "./pages/service/motorcycle/MotorcycleStock";
import NoPage from "./pages/NoPage";
import Login from "./pages/Login";
import Customer from "./pages/Customer";
import Bank from "./pages/Bank";
import User from "./pages/User";
import Account from "./pages/Account";

import {AuthenticationProvider} from "./security/authenticationProvider";


const App = () => {
    return (
        <StrictMode>
            <BrowserRouter>
                <AuthenticationProvider>
                    <Routes>
                        <Route path="/" element={<Layout/>}>
                            <Route index element={<Home/>}/>
                            <Route path="authentication">
                                <Route path="login" element={<Login/>}/>
                            </Route>
                            <Route path="service">
                                <Route index element={<Service/>}/>
                                <Route path="manufacturer" element={<Manufacturer/>}/>
                                <Route path="motorcycle">
                                    <Route index element={<Motorcycle/>}/>
                                    <Route path="model" element={<MotorcycleModel/>}/>
                                    <Route path="stock" element={<MotorcycleStock/>}/>
                                </Route>
                            </Route>
                            <Route path={"customer"} element={<Customer/>}/>
                            <Route path={"bank"} element={<Bank/>}/>
                            <Route path={"user"} element={<User/>}/>
                            <Route path={"user/*"} element={<Account/>}/>
                            <Route path="*" element={<NoPage/>}/>
                        </Route>
                    </Routes>
                </AuthenticationProvider>
            </BrowserRouter>
        </StrictMode>
    );
}

export default App;

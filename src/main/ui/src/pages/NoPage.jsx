import {Image} from "react-bootstrap";

import noPageImage from "../assets/img/error/areYouLost.jpg";


const NoPage = () => {
    return (<Image src={noPageImage}
                   alt="A good looking man sitting on a supermoto in the middle of nowhere,but in fact he is thinking that you are probably lost."/>);
};

export default NoPage;

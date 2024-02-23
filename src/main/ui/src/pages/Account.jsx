import {useContext} from "react";

import Card from "react-bootstrap/Card";
import {Image} from "react-bootstrap";

import underConstructionImage from "../assets/img/error/underConstruction.jpg"

import {AuthenticationContext} from "../security/authenticationProvider";


const Account = () => {
    const {username, userRole, userPermissions} = useContext(AuthenticationContext);


    return (
        <>
            <h2 className="page-title">My account</h2>
            {/*This part is only for temporarily testing the AuthenticationContext, the final page will be restructured.*/}
            <div style={{display: "flex", flexDirection: "column", alignItems: "flex-start"}}>
                <Card className={"bg-dark"}>
                    <Card.Body style={{textAlign: "left"}}>
                        <h5 style={{textDecoration: "underline"}}>Account data:</h5>
                        <h6>username: {username}</h6>
                        <h6>role: {userRole}</h6>
                        <h6>permissions: {userPermissions.join(', ')}</h6>
                    </Card.Body>
                </Card>
            </div>
            <Image style={{padding: "30px"}} src={underConstructionImage}/>
        </>
    )
}


export default Account;

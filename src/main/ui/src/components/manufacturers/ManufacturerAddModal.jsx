import {useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";


const ManufacturerAddModal = (props) => {
    const modalBody = (<AddForm setFormSubmit={props.setFormSubmit} setAddModalShow={props.setAddModalShow} />);

    return <CrudModal show={props.show} onHide={props.onHide} title="Add new Manufacturer" body={modalBody} />
}


const AddForm = (props) => {
    const [modalName, setModalName] = useState("");
    const [modalCountry, setModalCountry] = useState("");
    const [modalPartnerSince, setModalPartnerSince] = useState("");
    const [incompleteFormAlert, setIncompleteFormAlert] = useState("");

    const currentDate = new Date().toJSON().slice(0, 10);


    async function handleSubmit(event) {
        event.preventDefault();

        const url = "manufacturer/add";

        const requestBody = {
            "name": modalName,
            "country": modalCountry,
            "partnerSince": modalPartnerSince ? modalPartnerSince : currentDate
        }

        const options = {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody),
        }

        await fetch(url, options);
        props.setFormSubmit(old => !old);
    }


    return (
        <Form>
            <Form.Group className="mb-3" controlId="formTextareaName">
                <Form.Label>Manufacturer name<span style={{color: "red"}}>*</span></Form.Label>
                <Form.Control type="text" name="name" placeholder="Name"
                              onChange={(event) => setModalName(event.target.value)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formTextareaCountry">
                <Form.Label>Country of origin<span style={{color: "red"}}>*</span></Form.Label>
                <Form.Control type="text" name="country" placeholder="Country"
                              onChange={(event) => setModalCountry(event.target.value)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formDatePartnerSince">
                <Form.Label>Partner since</Form.Label>
                <Form.Control type="text" onFocus={(event) => (event.target.type = "date")}
                              onBlur={(event) => (event.target.type = "text")} defaultValue={currentDate}
                              name="partnerSince" onChange={(event) => setModalPartnerSince(event.target.value)}/>
            </Form.Group>

            <p style={{fontWeight: "bold", textAlign: "center"}}>{incompleteFormAlert}</p>

            <div style={{display: "flex", justifyContent: "center"}}>
                <Button type="button" variant="secondary" style={{paddingInline: "30px"}}
                        onClick={async (event) => {
                            if (!modalName || !modalCountry) {
                                setIncompleteFormAlert("Name and Country fields are both mandatory!");
                            } else {
                                await handleSubmit(event);
                                props.setAddModalShow(false);
                            }
                        }}>Update</Button>
            </div>
        </Form>
    )
}

export default ManufacturerAddModal;
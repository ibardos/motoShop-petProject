import {useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";
import {fetchBackendApi} from "../../util/fetchBackendApi";


const ManufacturerAddModal = (props) => {
    const modalBody = (<AddForm setFormSubmit={props.setFormSubmit} setAddModalShow={props.setAddModalShow} />);

    return <CrudModal show={props.show} onHide={props.onHide} title="Add new Manufacturer" body={modalBody} />
}


const AddForm = (props) => {
    const [name, setName] = useState("");
    const [country, setCountry] = useState("");
    const [partnerSince, setPartnerSince] = useState("");
    const [incompleteFormAlert, setIncompleteFormAlert] = useState("");

    const currentDate = new Date().toJSON().slice(0, 10);


    async function handleSubmit(event) {
        event.preventDefault();

        const body = {
            "name": name,
            "country": country,
            "partnerSince": partnerSince ? partnerSince : currentDate
        }

        try {
            await fetchBackendApi("/service/manufacturer/add", 'POST', body);
            props.setFormSubmit(old => !old);
        } catch (error) {
            console.error("Failed to add Manufacturer:", error.message);
        }
    }


    return (
        <Form>
            <Form.Group className="mb-3" controlId="formTextareaName">
                <Form.Label>Manufacturer name<span style={{color: "red"}}>*</span></Form.Label>
                <Form.Control type="text" name="name" placeholder="Name"
                              onChange={(event) => setName(event.target.value)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formTextareaCountry">
                <Form.Label>Country of origin<span style={{color: "red"}}>*</span></Form.Label>
                <Form.Control type="text" name="country" placeholder="Country"
                              onChange={(event) => setCountry(event.target.value)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formDatePartnerSince">
                <Form.Label>Partner since</Form.Label>
                <Form.Control type="text" onFocus={(event) => (event.target.type = "date")}
                              onBlur={(event) => (event.target.type = "text")} defaultValue={currentDate}
                              name="partnerSince" onChange={(event) => setPartnerSince(event.target.value)}/>
            </Form.Group>

            <p style={{fontWeight: "bold", textAlign: "center", color: "red"}}>{incompleteFormAlert}</p>

            <div style={{display: "flex", justifyContent: "center"}}>
                <Button type="button" variant="secondary" style={{paddingInline: "30px"}}
                        onClick={async (event) => {
                            if (!name || !country) {
                                setIncompleteFormAlert("Name and Country fields are both mandatory!");
                            } else {
                                await handleSubmit(event);
                                props.setAddModalShow(false);
                            }
                        }
                }>Add</Button>
            </div>
        </Form>
    )
}

export default ManufacturerAddModal;

import {useEffect, useState} from "react";

import {Form} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import CrudModal from "../shared/CrudModal";
import {fetchBackendApi} from "../../util/fetchBackendApi";


const ManufacturerUpdateModal = (props) => {
    const modalBody = (
        <UpdateForm manufacturers={props.manufacturers}
                    recordId={props.recordId}
                    setFormSubmit={props.setFormSubmit}
                    setUpdateModalShow={props.setUpdateModalShow}/>
    );

    return <CrudModal show={props.show} onHide={props.onHide} title="Update Manufacturer" body={modalBody} />
}


const UpdateForm = (props) => {
    const [name, setName] = useState("");
    const [country, setCountry] = useState("");
    const [partnerSince, setPartnerSince] = useState("");

    const [currentRecord, setCurrentRecord] = useState({});


    useEffect(() => {
        setCurrentRecord(props.manufacturers.find(m => m.id.toString() === props.recordId));
    }, [props.manufacturers, props.recordId])


    async function handleSubmit(event) {
        event.preventDefault();

        const body = {
            "id": currentRecord.id,
            "name": name ? name : currentRecord.name,
            "country": country ? country : currentRecord.country,
            "partnerSince": partnerSince ? partnerSince : currentRecord.partnerSince
        }

        if (name || country || partnerSince) {
            try {
                await fetchBackendApi("/service/manufacturer/update", "PUT", body);
                props.setFormSubmit(old => !old);
            } catch (error) {
                console.error("Failed to update Manufacturer:", error.message);
            }
        }
    }


    return (
        <Form onSubmit={async (event) => await handleSubmit(event)}>
            <Form.Group className="mb-3" controlId="formTextareaName">
                <Form.Label>Manufacturer name</Form.Label>
                <Form.Control type="text" name="name" defaultValue={currentRecord.name}
                              onChange={(event) => setName(event.target.value)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formTextareaCountry">
                <Form.Label>Country of origin</Form.Label>
                <Form.Control type="text" name="country" defaultValue={currentRecord.country}
                              onChange={(event) => setCountry(event.target.value)}/>
            </Form.Group>

            <Form.Group className="mb-3" controlId="formDatePartnerSince">
                <Form.Label>Partner since</Form.Label>
                <Form.Control type="text" onFocus={(event) => (event.target.type = "date")}
                              onBlur={(event) => (event.target.type = "text")}
                              defaultValue={currentRecord.partnerSince}
                              name="partnerSince" onChange={(event) => setPartnerSince(event.target.value)}/>
            </Form.Group>

            <div style={{display: "flex", justifyContent: "center"}}>
                <Button type="submit" variant="secondary" style={{paddingInline: "30px"}}
                        onClick={() => props.setUpdateModalShow(false)}>Update</Button>
            </div>
        </Form>
    )
}

export default ManufacturerUpdateModal;

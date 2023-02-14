import {useEffect, useState} from "react";

import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {FormControl} from "react-bootstrap";
import Container from "react-bootstrap/Container";
import Button from "react-bootstrap/Button";

import StripedTable from "../components/shared/table/StripedTable";
import ManufacturerAddModal from "../components/manufacturers/ManufacturerAddModal";

import {fetchData} from "../util/fetchData";


const Manufacturers = () => {
    // States for fetched data
    const [isLoaded, setIsLoaded] = useState(false);
    const [error, setError] = useState(null);
    const [manufacturers, setManufacturers] = useState([]);
    const [filteredData, setFilteredData] = useState([]);

    // States for Modals
    const [addModalShow, setAddModalShow] = useState(false);
    const [updateModalShow, setUpdateModalShow] = useState(false);
    const [deleteModalShow, setDeleteModalShow] = useState(false);
    const [recordId, setRecordId] = useState("");

    // State for table re-render
    const [formSubmit, setFormSubmit] = useState(false);


    // Table live search feature
    async function handleSearch(event) {
        setFilteredData(manufacturers.filter((item) =>
            item.id.toString().includes(event.target.value.toLowerCase()) ||
            item.name.toLowerCase().includes(event.target.value.toLowerCase()) ||
            item.country.toLowerCase().includes(event.target.value.toLowerCase()) ||
            item.partnerSince.toString().includes(event.target.value.toLowerCase())
        ));
    }


    // Fetching data for all the components of Manufacturers page
    useEffect(() => {
        fetchData("manufacturer/get/all")
            .then(
                (result) => {
                    setIsLoaded(true);
                    setManufacturers(result);
                    setFilteredData(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            );
    }, [formSubmit])

    // Check for errors during fetch data, and show loading status
    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    }


    return (
        <>
            <h2 className="page-title">Manufacturers</h2>
            <Container id="search-and-add-bar">
                <Row>
                    <Col>
                        <FormControl id="search-box" type="text" onChange={async (event) => await handleSearch(event)}
                                     placeholder="Type here to filter..."/>
                    </Col>
                    <Col xs={2}>
                        <Button variant="secondary" onClick={() => setAddModalShow(true)}>Add new item</Button>
                    </Col>
                </Row>
            </Container>

            <StripedTable originalData={manufacturers} filteredData={filteredData} setRecordId={setRecordId}
                          setUpdateModalShow={setUpdateModalShow} setDeleteModalShow={setDeleteModalShow}/>


            <ManufacturerAddModal setFormSubmit={setFormSubmit} show={addModalShow} setAddModalShow={setAddModalShow}
                                  onHide={() => setAddModalShow(false)}/>

        </>
    )
}

export default Manufacturers;
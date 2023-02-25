import {useEffect, useState} from "react";

import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {FormControl} from "react-bootstrap";
import Button from "react-bootstrap/Button";

import StripedTable from "../../components/shared/table/StripedTable";
import MotorcycleModelAddModal from "../../components/motorcycleModel/MotorcycleModelAddModal";
import MotorcycleModelUpdateModal from "../../components/motorcycleModel/MotorcycleModelUpdateModal";
import MotorcycleModelDeleteModal from "../../components/motorcycleModel/MotorcycleModelDeleteModal";
import MotorcycleModelDeleteErrorModal from "../../components/motorcycleModel/MotorcycleModelDeleteErrorModal";

import {fetchData} from "../../util/fetchData";


const MotorcycleModel = () => {
    // States for fetched data
    const [isLoaded, setIsLoaded] = useState(false);
    const [error, setError] = useState(null);
    const [motorcycleModels, setMotorcycleModels] = useState([]);
    const [filteredData, setFilteredData] = useState([]);

    // States for Modals
    const [addModalShow, setAddModalShow] = useState(false);
    const [updateModalShow, setUpdateModalShow] = useState(false);
    const [deleteModalShow, setDeleteModalShow] = useState(false);
    const [deleteErrorModalShow, setDeleteErrorModalShow] = useState(false);
    const [recordId, setRecordId] = useState("");

    // State for table re-render
    const [formSubmit, setFormSubmit] = useState(false);


    // Table live search feature
    async function handleSearch(event) {
        setFilteredData(motorcycleModels.filter((item) =>
            item.id.toString().includes(event.target.value.toLowerCase()) ||
            item.manufacturer.name.toLowerCase().includes(event.target.value.toLowerCase()) ||
            item.modelName.toString().includes(event.target.value.toLowerCase()) ||
            item.modelYear.toString().includes(event.target.value.toLowerCase()) ||
            item.weight.toString().includes(event.target.value.toLowerCase()) ||
            item.displacement.toString().includes(event.target.value.toLowerCase()) ||
            item.horsePower.toString().includes(event.target.value.toLowerCase()) ||
            item.topSpeed.toString().includes(event.target.value.toLowerCase()) ||
            item.gearbox.toString().includes(event.target.value.toLowerCase()) ||
            item.fuelCapacity.toString().includes(event.target.value.toLowerCase()) ||
            item.fuelConsumptionPer100Kms.toString().includes(event.target.value.toLowerCase()) ||
            item.motorcycleModelType.toLowerCase().includes(event.target.value.toLowerCase())
        ));
    }


    // Fetching data for all the components of Manufacturers page
    useEffect(() => {
        fetchData("/motorcycle/model/get/all")
            .then(
                (result) => {
                    setIsLoaded(true);
                    setMotorcycleModels(result);
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
            <h2 className="page-title">Motorcycle model</h2>
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

            <StripedTable originalData={motorcycleModels} filteredData={filteredData} setRecordId={setRecordId}
                          setUpdateModalShow={setUpdateModalShow} setDeleteModalShow={setDeleteModalShow}/>


            <MotorcycleModelAddModal setFormSubmit={setFormSubmit} show={addModalShow} setAddModalShow={setAddModalShow}
                                  onHide={() => setAddModalShow(false)}/>

            <MotorcycleModelUpdateModal motorcycleModels={motorcycleModels} recordId={recordId} setFormSubmit={setFormSubmit}
                                     show={updateModalShow} setUpdateModalShow={setUpdateModalShow}
                                     onHide={() => setUpdateModalShow(false)}/>

            <MotorcycleModelDeleteModal motorcycleModels={motorcycleModels} recordId={recordId}
                                     setErrorModalShow={setDeleteErrorModalShow} setFormSubmit={setFormSubmit}
                                     show={deleteModalShow} setDeleteModalShow={setDeleteModalShow}
                                     onHide={() => setDeleteModalShow(false)}/>

            <MotorcycleModelDeleteErrorModal motorcycleModels={motorcycleModels} recordId={recordId} show={deleteErrorModalShow}
                                          setDeleteErrorModalShow={setDeleteErrorModalShow}
                                          onHide={() => setDeleteErrorModalShow(false)}/>
        </>
    )
};

export default MotorcycleModel;
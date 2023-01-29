import {Table} from "react-bootstrap";
import {useEffect, useState} from "react";
const Manufacturers = () => {
    const [error, setError] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [manufacturers, setManufacturers] = useState([]);

    // Fetching data
    useEffect(() => {
        fetch("manufacturer/get/all")
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setManufacturers(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setError(error);
                }
            )
    }, [])

    // Check errors and show loading status
    if (error) {
        return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
        return <div>Loading...</div>;
    }

    return (
        <>
            <h2 className="pageTitle">Manufacturers</h2>
            <ManufacturersTable manufacturers={manufacturers} />
        </>
    )
};

const ManufacturersTable = (props) => {


    return (
        <Table striped bordered hover variant="dark">
            <thead>
            <tr>
                <th>id</th>
                <th>Name</th>
                <th>Country</th>
                <th>Partner since</th>
            </tr>
            </thead>
            <tbody>
            {props.manufacturers.map(m => (
                <tr key={m.id}>
                    <td>{m.id}</td>
                    <td>{m.name}</td>
                    <td>{m.country}</td>
                    <td>{m.partnerSince}</td>
                </tr>
            ))}
            </tbody>
        </Table>
    )
}

export default Manufacturers;
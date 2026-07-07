import { useState } from "react";
import API from "../services/api";

function Upload() {

    const [file, setFile] = useState(null);
    const [message, setMessage] = useState("");

    const upload = async () => {

        if (!file) {
            alert("Choose a PDF");
            return;
        }

        const formData = new FormData();
        formData.append("file", file);

        try {

            const response = await API.post("/upload", formData, {
                headers: {
                    "Content-Type": "multipart/form-data"
                }
            });

            setMessage(response.data);

        } catch (error) {

            setMessage("Upload Failed");

        }

    };

    return (

        <div className="card p-4 shadow">

            <h4>Upload PDF</h4>

            <input
                type="file"
                className="form-control mt-3"
                accept=".pdf"
                onChange={(e) => setFile(e.target.files[0])}
            />

            <button
                className="btn btn-primary mt-3"
                onClick={upload}
            >
                Upload
            </button>

            <p className="mt-3 text-success">
                {message}
            </p>

        </div>

    );

}

export default Upload;
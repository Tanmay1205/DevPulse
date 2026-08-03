import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    TextField,
    Button,
    MenuItem,
    Stack,
} from "@mui/material";

import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import "./TaskDialog.css";
import {
    createTask,
    updateTask,
} from "../../services/taskService";

function TaskDialog({
                        open,
                        onClose,
                        onSuccess,
                        task: selectedTask,
                    }) {

    const [form, setForm] = useState({
        title: "",
        description: "",
        status: "TODO",
        priority: "MEDIUM",
        dueDate: "",
    });

    const [loading, setLoading] = useState(false);

    useEffect(() => {

        if (selectedTask) {

            setForm({
                title: selectedTask.title || "",
                description: selectedTask.description || "",
                status: selectedTask.status || "TODO",
                priority: selectedTask.priority || "MEDIUM",
                dueDate: selectedTask.dueDate || "",
            });

        } else {

            setForm({
                title: "",
                description: "",
                status: "TODO",
                priority: "MEDIUM",
                dueDate: "",
            });

        }

    }, [selectedTask, open]);

    const handleChange = (e) => {

        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });

    };

    const handleSave = async () => {

        if (!form.title.trim()) {
            toast.error("Title is required");
            return;
        }

        try {

            setLoading(true);

            if (selectedTask) {

                await updateTask(selectedTask.id, form);

                toast.success("Task updated successfully");

            } else {

                await createTask(form);

                toast.success("Task created successfully");

            }

            onSuccess();

            onClose();

        } catch (e) {

            console.error(e);

            toast.error("Something went wrong");

        } finally {

            setLoading(false);

        }

    };

    return (

        <Dialog
            open={open}
            onClose={onClose}
            fullWidth
            maxWidth="sm"
            PaperProps={{
                className:"dialog-paper"
            }}
        >

            <DialogTitle
                className="dialog-header"
            >
                {selectedTask ? "Edit Task" : "Create Task"}
            </DialogTitle>

            <DialogContent>
                className="dialog-content"


                <Stack spacing={2} mt={2}>

                    <TextField
                        className="dialog-input"
                        label="Title"
                        name="title"
                        value={form.title}
                        onChange={handleChange}
                        fullWidth
                        required
                    />

                    <TextField
                        className="dialog-input"
                        label="Description"
                        name="description"
                        value={form.description}
                        onChange={handleChange}
                        multiline
                        rows={3}
                        fullWidth
                    />

                    <TextField
                        className="dialog-input"
                        select
                        label="Status"
                        name="status"
                        value={form.status}
                        onChange={handleChange}
                        fullWidth
                    >
                        <MenuItem value="TODO">TODO</MenuItem>
                        <MenuItem value="IN_PROGRESS">IN PROGRESS</MenuItem>
                        <MenuItem value="COMPLETED">COMPLETED</MenuItem>
                    </TextField>

                    <TextField
                        className="dialog-input"
                        select
                        label="Priority"
                        name="priority"
                        value={form.priority}
                        onChange={handleChange}
                        fullWidth
                    >
                        <MenuItem value="LOW">LOW</MenuItem>
                        <MenuItem value="MEDIUM">MEDIUM</MenuItem>
                        <MenuItem value="HIGH">HIGH</MenuItem>
                    </TextField>

                    <TextField
                        className="dialog-input"
                        label="Due Date"
                        type="date"
                        name="dueDate"
                        value={form.dueDate}
                        onChange={handleChange}
                        InputLabelProps={{
                            shrink: true,
                        }}
                        fullWidth
                    />

                </Stack>

            </DialogContent>

            <DialogActions>

                <Button
                    className="cancel-btn"
                    onClick={onClose}
                >
                    Cancel
                </Button>

                <Button
                    className="save-btn"
                    variant="contained"
                    onClick={handleSave}
                >
                    {selectedTask ? "Update Task" : "Create Task"}
                </Button>

            </DialogActions>

        </Dialog>

    );

}

export default TaskDialog;
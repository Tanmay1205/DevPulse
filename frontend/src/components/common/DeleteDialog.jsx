import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogContentText,
    DialogActions,
    Button,
} from "@mui/material";

function DeleteDialog({
                          open,
                          onClose,
                          onConfirm,
                          title = "Delete",
                          message = "Are you sure?"
                      }) {

    return (
        <Dialog
            open={open}
            onClose={onClose}
        >

            <DialogTitle>
                {title}
            </DialogTitle>

            <DialogContent>

                <DialogContentText>
                    {message}
                </DialogContentText>

            </DialogContent>

            <DialogActions>

                <Button onClick={onClose}>
                    Cancel
                </Button>

                <Button
                    color="error"
                    variant="contained"
                    onClick={onConfirm}
                >
                    Delete
                </Button>

            </DialogActions>

        </Dialog>
    );

}

export default DeleteDialog;
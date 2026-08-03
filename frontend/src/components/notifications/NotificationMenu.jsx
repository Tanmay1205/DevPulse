import {
    Menu,
    MenuItem,
    Typography,
    Divider,
    Box,
} from "@mui/material";

function NotificationMenu({

                              anchorEl,

                              open,

                              onClose,

                              notifications,

                          }) {

    return (

        <Menu
            anchorEl={anchorEl}
            open={open}
            onClose={onClose}
            PaperProps={{
                sx:{
                    width:340,
                    borderRadius:3,
                    mt:1,
                    p:1
                }
            }}
        >

            <Typography
                fontWeight="bold"
                px={2}
                py={1}
            >
                Notifications
            </Typography>

            <Divider/>

            {

                notifications.length===0?

                    <MenuItem>

                        No Notifications

                    </MenuItem>

                    :

                    notifications.map((item,index)=>(

                        <MenuItem
                            key={index}
                        >

                            <Box>

                                <Typography
                                    fontWeight={600}
                                >
                                    {item.title}
                                </Typography>

                                <Typography
                                    variant="body2"
                                    color="text.secondary"
                                >
                                    {item.message}
                                </Typography>

                            </Box>

                        </MenuItem>

                    ))

            }

        </Menu>

    );

}

export default NotificationMenu;
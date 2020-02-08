#include <ClanLib/core.h>
#include <ClanLib/application.h>
#include <ClanLib/display.h>

#include "Game.h"


class Zelda : public CL_ClanApplication {

public:
	// This function returns the name of your game
	virtual char *get_title() {

		return "The Legend of Zelda";
	}

	virtual int main(int argc, char **argv)	{

		


		// Create a console window for text-output if not available
		CL_ConsoleWindow console("Console");
		console.redirect_stdio();

		try {
			CL_SetupCore::init();
			CL_SetupDisplay::init();
			CL_SetupSound::init();


			Game theGame('a');

			CL_SetupSound::deinit();
			CL_SetupDisplay::deinit();
			CL_SetupCore::deinit();
		}
		catch(CL_Error error)
		{
			std::cout << "Exception caught : " << error.message.c_str() << std::endl;			

			// Display console close message and wait for a key
			console.display_close_message();

			return -1;
		}

		return 0;
	}
} my_app;

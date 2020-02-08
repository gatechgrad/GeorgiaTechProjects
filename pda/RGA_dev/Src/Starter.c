/******************************************************************************
 *
 * Copyright (c) 1999 Palm Computing, Inc. or its subsidiaries.
 * All rights reserved.
 *
 * File: Starter.c
 *
 *****************************************************************************/

#include <PalmOS.h>
#include "StarterRsc.h"

#include "Restaurant.h"




char strName[25] = "";
char strCuisine[25] = "Any";
int iPriceFrom = -1;
int iPriceTo = -1;
char strCity[25] = "";
char strState[2] = "";
int iTimeFrom = -1;
int iTimeTo = -1;
char strPymts[25] = "Any";

int iSelected;
int iSelectedIndex;
char *choices[MAX_RESTAURANTS];
int choiceMappings[MAX_RESTAURANTS];



/***********************************************************************
 *
 *   Entry Points
 *
 ***********************************************************************/
//void setupData();
static Boolean ResultsFormHandleEvent(EventPtr eventP);
void getRestaurants(Boolean matched[MAX_RESTAURANTS]);

/***********************************************************************
 *
 *   Internal Structures
 *
 ***********************************************************************/
typedef struct 
	{
	UInt8 replaceme;
	} StarterPreferenceType;

typedef struct 
	{
	UInt8 replaceme;
	} StarterAppInfoType;

typedef StarterAppInfoType* StarterAppInfoPtr;


/***********************************************************************
 *
 *   Global variables
 *
 ***********************************************************************/
//static Boolean HideSecretRecords;


/***********************************************************************
 *
 *   Internal Constants
 *
 ***********************************************************************/
#define appFileCreator				 'STRT'
#define appVersionNum              0x01
#define appPrefID                  0x00
#define appPrefVersionNum          0x01

// Define the minimum OS version we support (2.0 for now).
#define ourMinVersion	sysMakeROMVersion(2,0,0,sysROMStageRelease,0)


/***********************************************************************
 *
 *   Internal Functions
 *
 ***********************************************************************/


/***********************************************************************
 *
 * FUNCTION:    RomVersionCompatible
 *
 * DESCRIPTION: This routine checks that a ROM version is meet your
 *              minimum requirement.
 *
 * PARAMETERS:  requiredVersion - minimum rom version required
 *                                (see sysFtrNumROMVersion in SystemMgr.h 
 *                                for format)
 *              launchFlags     - flags that indicate if the application 
 *                                UI is initialized.
 *
 * RETURNED:    error code or zero if rom is compatible
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static Err RomVersionCompatible(UInt32 requiredVersion, UInt16 launchFlags)
{
	UInt32 romVersion;

	// See if we're on in minimum required version of the ROM or later.
	FtrGet(sysFtrCreator, sysFtrNumROMVersion, &romVersion);
	if (romVersion < requiredVersion)
		{
		if ((launchFlags & (sysAppLaunchFlagNewGlobals | sysAppLaunchFlagUIApp)) ==
			(sysAppLaunchFlagNewGlobals | sysAppLaunchFlagUIApp))
			{
			FrmAlert (RomIncompatibleAlert);
		
			// Palm OS 1.0 will continuously relaunch this app unless we switch to 
			// another safe one.
			if (romVersion < ourMinVersion)
				{
				AppLaunchWithCommand(sysFileCDefaultApp, sysAppLaunchCmdNormalLaunch, NULL);
				}
			}
		
		return sysErrRomIncompatible;
		}

	return errNone;
}


/***********************************************************************
 *
 * FUNCTION:    GetObjectPtr
 *
 * DESCRIPTION: This routine returns a pointer to an object in the current
 *              form.
 *
 * PARAMETERS:  formId - id of the form to display
 *
 * RETURNED:    void *
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static void * GetObjectPtr(UInt16 objectID)
{
	FormPtr frmP;

	frmP = FrmGetActiveForm();
	return FrmGetObjectPtr(frmP, FrmGetObjectIndex(frmP, objectID));
}


/***********************************************************************
 *
 * FUNCTION:    MainFormInit
 *
 * DESCRIPTION: This routine initializes the MainForm form.
 *
 * PARAMETERS:  frm - pointer to the MainForm form.
 *
 * RETURNED:    nothing
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static void MainFormInit(FormPtr /*frmP*/)
{
}



/***********************************************************************
 *
 * FUNCTION:    MainFormDoCommand
 *
 * DESCRIPTION: This routine performs the menu command specified.
 *
 * PARAMETERS:  command  - menu item id
 *
 * RETURNED:    nothing
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static Boolean MainFormDoCommand(UInt16 command)
{
	Boolean handled = false;
   FormPtr frmP;

	switch (command)
		{
		case MainOptionsAboutStarterApp:
			MenuEraseStatus(0);					// Clear the menu status from the display.
			frmP = FrmInitForm (AboutForm);
			FrmDoDialog (frmP);					// Display the About Box.
			FrmDeleteForm (frmP);
			handled = true;
			break;

		}
	
	return handled;
}


/***********************************************************************
 *
 * FUNCTION:    MainFormHandleEvent
 *
 * DESCRIPTION: This routine is the event handler for the 
 *              "MainForm" of this application.
 *
 * PARAMETERS:  eventP  - a pointer to an EventType structure
 *
 * RETURNED:    true if the event has handle and should not be passed
 *              to a higher level handler.
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static Boolean MainFormHandleEvent(EventPtr eventP) {
   Boolean handled = false;
   FormPtr frmP;
   char strTemp[50];
	
	switch (eventP->eType) 
		{
		case menuEvent:
			return MainFormDoCommand(eventP->data.menu.itemID);

		case frmOpenEvent:
			frmP = FrmGetActiveForm();
			MainFormInit( frmP);
			FrmDrawForm ( frmP);
			
			//set last used (or default values)
			FldInsert((FieldType *) GetObjectPtr(MainNameField), strName, StrLen(strName));			

			if (iPriceFrom > -1) {
				StrIToA(strTemp, iPriceFrom);
				FldInsert((FieldType *) GetObjectPtr(MainPriceFromField), strTemp, StrLen(strTemp));
			}
			
			if (iPriceTo > -1) {
				StrIToA(strTemp, iPriceTo);
				FldInsert((FieldType *) GetObjectPtr(MainPriceToField), strTemp, StrLen(strTemp));
			}
			
			FldInsert((FieldType *) GetObjectPtr(MainCityField), strCity, StrLen(strCity));
			
			if (iTimeFrom > -1) {
				StrIToA(strTemp, iTimeFrom);
				FldInsert((FieldType *) GetObjectPtr(MainTimeFromField), strTemp, StrLen(strTemp));
			}
			
			if (iTimeTo > -1) {
				StrIToA(strTemp, iTimeTo);
				FldInsert((FieldType *) GetObjectPtr(MainTimeToField), strTemp, StrLen(strTemp));
			}


			


			handled = true;
			break;

		case ctlSelectEvent: {

			switch (eventP->data.ctlSelect.controlID) {
				case MainSearchButton:
					frmP = FrmGetActiveForm();  //frmP == FrmGetFromPtr(FormMain)
					
					//Name
					if (FldGetTextPtr((FieldType *) GetObjectPtr(MainNameField)) != NULL) {
						StrToLower(strName, FldGetTextPtr((FieldType *) GetObjectPtr(MainNameField)));
					}

					//Cuisine
					StrToLower(strCuisine, CtlGetLabel((ControlType *) GetObjectPtr(MainCuisinePopTrigger)));
					
					//From Price
					if (FldGetTextPtr((FieldType *) GetObjectPtr(MainPriceFromField)) != NULL) {
						iPriceFrom = StrAToI(FldGetTextPtr((FieldType *) GetObjectPtr(MainPriceFromField)));
					} else {
						iPriceFrom = -1;
					}
					
					//To Price
					if (FldGetTextPtr((FieldType *) GetObjectPtr(MainPriceToField)) != NULL) {
						iPriceTo = StrAToI(FldGetTextPtr((FieldType *) GetObjectPtr(MainPriceToField)));
					} else {
						iPriceTo = -1;
					}

					//City
					if (FldGetTextPtr((FieldType *) GetObjectPtr(MainCityField)) != NULL) {
						StrToLower(strCity, FldGetTextPtr((FieldType *) GetObjectPtr(MainCityField)));
					}

					//State
					StrToLower(strState, CtlGetLabel((ControlType *) GetObjectPtr(MainStatePopTrigger)));

					//Time From
					if (FldGetTextPtr((FieldType *) GetObjectPtr(MainTimeFromField)) != NULL) {
						iTimeFrom = StrAToI(FldGetTextPtr((FieldType *) GetObjectPtr(MainTimeFromField)));
						
						if (StrCaselessCompare(CtlGetLabel((ControlType *) GetObjectPtr(MainTimeFromPopTrigger)), "pm") == 0) {
							iTimeFrom += 1200;
						}
					} else {
						iTimeFrom = -1;
					}

					//Time To
					if (FldGetTextPtr((FieldType *) GetObjectPtr(MainTimeToField)) != NULL) {
						iTimeTo = StrAToI(FldGetTextPtr((FieldType *) GetObjectPtr(MainTimeToField)));
						
						if (StrCaselessCompare(CtlGetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, MainTimeToPopTrigger)))), "pm") == 0) {
							iTimeTo += 1200;
						}

					} else {
						iTimeTo = -1;
					}

					//Payments Accepted
					StrToLower(strPymts, CtlGetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, MainPaymentsAcceptedPopTrigger)))));


				    FrmGotoForm(ResultsForm);
	    			handled = true;
					break;
					
				default:
					break;
			}
		}
			

		default:
			break;
		
		}
	
	return handled;
}




static Boolean ResultsFormHandleEvent(EventPtr eventP) {
   Boolean handled = false;
   FormPtr frmP;
   int i;
   int iMatches;

   
   Boolean matched[MAX_RESTAURANTS];
   

	switch (eventP->eType) 	{

		case frmOpenEvent: {
			frmP = FrmGetActiveForm();

			getRestaurants(matched);

			
			iMatches = 0;
			for (i = 0; i < MAX_RESTAURANTS; i++) {
				if (matched[i] == true) {
					choices[iMatches] = data[i].strName;
					choiceMappings[iMatches] = i;
					iMatches++;
				}

				
			}

			LstSetListChoices((ListType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, ResultsResultsList))), choices, iMatches);
			
			if (iSelected > -1) {
				LstSetSelection((ListType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, ResultsResultsList))), iSelected);
				LstSetTopItem((ListType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, ResultsResultsList))), iSelected);
			}

			
			FrmDrawForm ( frmP);
			handled = true;
			break;
		}
		
		case ctlSelectEvent: {
			handled = true;		// assume we'll handle it

			frmP = FrmGetActiveForm();
			iSelected = LstGetSelection((ListType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, ResultsResultsList))));
			iSelectedIndex = choiceMappings[iSelected];

			switch (eventP->data.ctlSelect.controlID) {
				case ResultsSearchButton:
					iSelected = -1;
				    FrmGotoForm(MainForm);
					break;
				case ResultsDetailsButton:
					FrmGotoForm(DetailsForm);
					break;
				case ResultsDirectionsButton:
					FrmGotoForm(DirectionsForm);
					break;
				case ResultsMenuButton:
					FrmGotoForm(MenuForm);
					break;
				case ResultsWaitTimeButton:
					FrmGotoForm(WaitTimeForm);
					break;
				case ResultsPlaceOrderButton:
					FrmGotoForm(PlaceOrderForm);
					break;

				
				default:
					break;
			}
		}
			
		default:
			break;
		
	}
	
	return handled;
}


void getRestaurants(Boolean matched[MAX_RESTAURANTS]) {
	int i;
	char strTemp[50];
	Boolean hasMatched;
	
	
	for (i = 0; i < MAX_RESTAURANTS; i++) {
		hasMatched = true;

		if ((strName != NULL) && (StrCompare(strName, "") != 0)) {
			StrToLower(strTemp, data[i].strName);
			if (StrStr(strTemp, strName) == NULL) {
				hasMatched = false;
			}
		}
				
		if ((strCuisine != NULL) && (StrCaselessCompare(strCuisine, "Any") != 0)) {
			if (StrCaselessCompare(data[i].strCuisine, strCuisine) != 0) {
				hasMatched = false;
			}
		}

/*
		if (iPriceFrom > -1) {
			if (data[i].iPriceFrom < iPriceFrom) {
				hasMatched = false;
			}
		}
		
		if (iPriceTo > -1) {
			if (data[i].iPriceTo > iPriceTo) {
				hasMatched = false;
			}
		}
		
		if (iTimeFrom > -1) {
			if (data[i].iTimeFrom < iTimeFrom) {
				hasMatched = false;
			}
		}
		
		if (iTimeTo > -1) {
			if (data[i].iTimeFrom > iTimeTo) {
				hasMatched = false;
			}
		}
		
		if ((strCity != NULL) && (StrCompare(strCity, "") != 0)) {
			if (StrStr(data[i].strCity, strCity) == NULL) {
				hasMatched = false;
			}
		}


		if ((strState != NULL) && (StrCaselessCompare(strState, "Any") != 0)) {
			if (StrCaselessCompare(data[i].strState, strState) != 0) {
				hasMatched = false;
			}
		}

		if ((strPymts != NULL) && (StrCaselessCompare(strPymts, "Any") != 0)) {
			if ((StrCaselessCompare(strPymts, "Cash") == 0) && (data[i].iCash == false)) {
				hasMatched = false;
			}
			
			if ((StrCaselessCompare(strPymts, "Credit Card") == 0) && (data[i].iCreditCards == false)) {
				hasMatched = false;
			}

			if ((StrCaselessCompare(strPymts, "Check") == 0) && (data[i].iChecks == false)) {
				hasMatched = false;
			}
		}

*/			
		if (hasMatched) {
			matched[i] = true;
		} else {
			matched[i] = false;
		}	
			
	}
	
}



static Boolean DetailsFormHandleEvent(EventPtr eventP) {
   Boolean handled = false;
   FormPtr frmP;
//   char strTemp[50];
//   char strIntTemp[50];

	switch (eventP->eType) 	{

		case frmOpenEvent: {
			frmP = FrmGetActiveForm();
			
			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsName2Label))), data[iSelectedIndex].strName);
			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsNameLabel))), data[iSelectedIndex].strCuisine);
//			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsCuisine2Label))), data[iSelected].strCuisine);
			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsPhoneLabel))), "770-555-1234");
			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsAddressLabel))), data[iSelectedIndex].strAddress);
			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsCityLabel))), data[iSelectedIndex].strCity);
			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsStateLabel))), data[iSelectedIndex].strState);


/*
			//cannot use correct algorithm due to limtied functionality of demo
			StrCopy(strTemp, "$");
			StrIToA(strIntTemp, data[iSelected].iPriceFrom);
			StrCat(strTemp, strIntTemp);
			StrCat(strTemp, " to $");
			StrIToA(strIntTemp, data[iSelected].iPriceTo);
			StrCat(strTemp, strIntTemp);
*/			


			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsPriceRangeLabel))), "$5 to $15");


			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsHoursLabel))), "8:00am to 11:00pm");
			
			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, DetailsPaymentsAcceptedLabel))), "Csh, Chk, CC");

			FrmDrawForm ( frmP);
			handled = true;
			break;
		}
		
		case ctlSelectEvent: {
			handled = true;		// assume we'll handle it
			switch (eventP->data.ctlSelect.controlID) {
				case DetailsBackButton:
					FrmGotoForm(ResultsForm);
					break;
				default:
					break;
			}
		}
			
		default:
			break;
		
	}
	
	return handled;
}

static Boolean DirectionsFormHandleEvent(EventPtr eventP) {
   Boolean handled = false;
   FormPtr frmP;

	switch (eventP->eType) 	{

		case frmOpenEvent: {
			frmP = FrmGetActiveForm();
			
			FrmDrawForm ( frmP);
			handled = true;
			break;
		}
		
		case ctlSelectEvent: {
			handled = true;		// assume we'll handle it
			switch (eventP->data.ctlSelect.controlID) {
				case DirectionsBackButton:
					FrmGotoForm(ResultsForm);
					break;
				default:
					break;
			}
		}
			
		default:
			break;
		
	}
	
	return handled;
}


static Boolean MenuFormHandleEvent(EventPtr eventP) {
   Boolean handled = false;
   FormPtr frmP;
   char strTemp[50];

	switch (eventP->eType) 	{

		case frmOpenEvent: {
			frmP = FrmGetActiveForm();

			StrCopy(strTemp, "Menu for ");
			StrCat(strTemp, data[iSelectedIndex].strName);
			CtlSetLabel((ControlType *) GetObjectPtr(MenuNameLabel), strTemp);

			FrmDrawForm ( frmP);
			handled = true;
			break;
		}
		
		case ctlSelectEvent: {
			handled = true;		// assume we'll handle it
			switch (eventP->data.ctlSelect.controlID) {
				case MenuBackButton:
					FrmGotoForm(ResultsForm);
					break;
				default:
					break;
			}
		}
			
		default:
			break;
		
	}
	
	return handled;
}

static Boolean PlaceOrderFormHandleEvent(EventPtr eventP) {
   Boolean handled = false;
   FormPtr frmP;

	switch (eventP->eType) 	{

		case frmOpenEvent: {
			frmP = FrmGetActiveForm();
			
			FrmDrawForm ( frmP);
			handled = true;
			break;
		}
		
		case ctlSelectEvent: {
			handled = true;		// assume we'll handle it
			switch (eventP->data.ctlSelect.controlID) {
				case PlaceOrderBackButton:
					FrmGotoForm(ResultsForm);
					break;
				default:
					break;
			}
		}
			
		default:
			break;
		
	}
	
	return handled;
}

static Boolean WaitTimeFormHandleEvent(EventPtr eventP) {
   Boolean handled = false;
   FormPtr frmP;
   char strTemp[100];

	switch (eventP->eType) 	{

		case frmOpenEvent: {
			frmP = FrmGetActiveForm();
			
			StrCopy(strTemp, "The wait time for ");
			StrCat(strTemp, data[iSelectedIndex].strName);
			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, WaitTimeRestaurantLabel))), strTemp);
			
			CtlSetLabel((ControlType *) FrmGetObjectPtr(frmP, (FrmGetObjectIndex (frmP, WaitTimeMinutesLabel))), "is 30 minutes");

			
			FrmDrawForm ( frmP);
			handled = true;
			break;
		}
		
		case ctlSelectEvent: {
			handled = true;		// assume we'll handle it
			switch (eventP->data.ctlSelect.controlID) {
				case WaitTimeBackButton:
					FrmGotoForm(ResultsForm);
					break;
				case WaitTimeMakeReservationsButton:
					FrmGotoForm(MakeReservationForm);
					break;
				default:
					break;
			}
		}
			
		default:
			break;
		
	}
	
	return handled;
}


static Boolean MakeReservationFormHandleEvent(EventPtr eventP) {
   Boolean handled = false;
   FormPtr frmP;

	switch (eventP->eType) 	{

		case frmOpenEvent: {
			frmP = FrmGetActiveForm();
			
			FrmDrawForm ( frmP);
			handled = true;
			break;
		}
		
		case ctlSelectEvent: {
			handled = true;		// assume we'll handle it
			switch (eventP->data.ctlSelect.controlID) {
				case MakeReservationRequestButton:
//					FrmGotoForm(ResultsForm);
					break;
				case MakeReservationBackButton:
					FrmGotoForm(ResultsForm);
					break;
				default:
					break;
			}
		}
			
		default:
			break;
		
	}
	
	return handled;
}



/***********************************************************************
 *
 * FUNCTION:    AppHandleEvent
 *
 * DESCRIPTION: This routine loads form resources and set the event
 *              handler for the form loaded.
 *
 * PARAMETERS:  event  - a pointer to an EventType structure
 *
 * RETURNED:    true if the event has handle and should not be passed
 *              to a higher level handler.
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static Boolean AppHandleEvent(EventPtr eventP)
{
	UInt16 formId;
	FormPtr frmP;

	if (eventP->eType == frmLoadEvent)
		{
		// Load the form resource.
		formId = eventP->data.frmLoad.formID;
		frmP = FrmInitForm(formId);
		FrmSetActiveForm(frmP);

		// Set the event handler for the form.  The handler of the currently
		// active form is called by FrmHandleEvent each time is receives an
		// event.
		switch (formId)
			{
			case MainForm:
				FrmSetEventHandler(frmP, MainFormHandleEvent);
				break;
			case ResultsForm:
				FrmSetEventHandler(frmP, ResultsFormHandleEvent);
				break;
			case DetailsForm:
				FrmSetEventHandler(frmP, DetailsFormHandleEvent);
				break;
			case DirectionsForm:
				FrmSetEventHandler(frmP, DirectionsFormHandleEvent);
				break;
			case MenuForm:
				FrmSetEventHandler(frmP, MenuFormHandleEvent);
				break;
			case PlaceOrderForm:
				FrmSetEventHandler(frmP, PlaceOrderFormHandleEvent);
				break;
			case WaitTimeForm:
				FrmSetEventHandler(frmP, WaitTimeFormHandleEvent);
				break;
			case MakeReservationForm:
				FrmSetEventHandler(frmP, MakeReservationFormHandleEvent);
				break;

			default:
//				ErrFatalDisplay("Invalid Form Load Event");
				break;

			}
		return true;
		}
	
	return false;
}


/***********************************************************************
 *
 * FUNCTION:    AppEventLoop
 *
 * DESCRIPTION: This routine is the event loop for the application.  
 *
 * PARAMETERS:  nothing
 *
 * RETURNED:    nothing
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static void AppEventLoop(void)
{
	UInt16 error;
	EventType event;

	do {
		EvtGetEvent(&event, evtWaitForever);

		if (! SysHandleEvent(&event))
			if (! MenuHandleEvent(0, &event, &error))
				if (! AppHandleEvent(&event))
					FrmDispatchEvent(&event);

	} while (event.eType != appStopEvent);
}


/***********************************************************************
 *
 * FUNCTION:     AppStart
 *
 * DESCRIPTION:  Get the current application's preferences.
 *
 * PARAMETERS:   nothing
 *
 * RETURNED:     Err value 0 if nothing went wrong
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static Err AppStart(void)
{
    StarterPreferenceType prefs;
    UInt16 prefsSize;
    
    //setupData(data);

	// Read the saved preferences / saved-state information.
	prefsSize = sizeof(StarterPreferenceType);
	if (PrefGetAppPreferences(appFileCreator, appPrefID, &prefs, &prefsSize, true) != 
		noPreferenceFound)
		{
		}
	
   return errNone;
}


/***********************************************************************
 *
 * FUNCTION:    AppStop
 *
 * DESCRIPTION: Save the current state of the application.
 *
 * PARAMETERS:  nothing
 *
 * RETURNED:    nothing
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static void AppStop(void)
{
   StarterPreferenceType prefs;

	// Write the saved preferences / saved-state information.  This data 
	// will be backed up during a HotSync.
	PrefSetAppPreferences (appFileCreator, appPrefID, appPrefVersionNum, 
		&prefs, sizeof (prefs), true);
		
	// Close all the open forms.
	FrmCloseAllForms ();
}


/***********************************************************************
 *
 * FUNCTION:    StarterPalmMain
 *
 * DESCRIPTION: This is the main entry point for the application.
 *
 * PARAMETERS:  cmd - word value specifying the launch code. 
 *              cmdPB - pointer to a structure that is associated with the launch code. 
 *              launchFlags -  word value providing extra information about the launch.
 *
 * RETURNED:    Result of launch
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
static UInt32 StarterPalmMain(UInt16 cmd, MemPtr /*cmdPBP*/, UInt16 launchFlags)
{
	Err error;

	error = RomVersionCompatible (ourMinVersion, launchFlags);
	if (error) return (error);

	switch (cmd)
		{
		case sysAppLaunchCmdNormalLaunch:
			error = AppStart();
			if (error) 
				return error;
				
			FrmGotoForm(MainForm);
			AppEventLoop();
			AppStop();
			break;

		default:
			break;

		}
	
	return errNone;
}


/***********************************************************************
 *
 * FUNCTION:    PilotMain
 *
 * DESCRIPTION: This is the main entry point for the application.
 *
 * PARAMETERS:  cmd - word value specifying the launch code. 
 *              cmdPB - pointer to a structure that is associated with the launch code. 
 *              launchFlags -  word value providing extra information about the launch.
 * RETURNED:    Result of launch
 *
 * REVISION HISTORY:
 *
 *
 ***********************************************************************/
UInt32 PilotMain( UInt16 cmd, MemPtr cmdPBP, UInt16 launchFlags)
{
    return StarterPalmMain(cmd, cmdPBP, launchFlags);
}



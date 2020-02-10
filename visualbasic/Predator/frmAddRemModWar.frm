VERSION 5.00
Begin VB.Form frmAddRemModWar 
   Caption         =   "Add/Remove/Modify Warrant"
   ClientHeight    =   2985
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4470
   LinkTopic       =   "Form8"
   ScaleHeight     =   2985
   ScaleWidth      =   4470
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdCancel 
      Caption         =   "Cancel"
      Height          =   615
      Left            =   240
      TabIndex        =   3
      Top             =   2040
      Width           =   1575
   End
   Begin VB.CommandButton cmdCrNew 
      Caption         =   "Create new"
      Height          =   615
      Left            =   240
      TabIndex        =   2
      Top             =   1200
      Width           =   1575
   End
   Begin VB.CommandButton cmdModEx 
      Caption         =   "Modify Existing"
      Height          =   615
      Left            =   240
      TabIndex        =   1
      Top             =   360
      Width           =   1575
   End
   Begin VB.TextBox txtExWarNum 
      Height          =   285
      Left            =   2040
      TabIndex        =   0
      Top             =   480
      Width           =   2175
   End
End
Attribute VB_Name = "frmAddRemModWar"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim dbPredator As Database
Private Sub cmdCancel_Click()
    frmAddRemModWar.Visible = False
    frmDataMan.Visible = True
End Sub

Private Sub cmdCrNew_Click()

    frmModWarrant.setWarrant (getAutoNumber("warrants", "id"))
            frmAddRemModWar.Visible = False
            frmModWarrant.Visible = True

'******************* OLD CODE *****************************

'    If IsNumeric(txtExWarNum.text) Then
    
'        Set dbPredator = OpenDatabase("Predator.mdb")

        'Check to see if the warrant number specified is already in the database
'        strQuery = "SELECT * FROM warrants " & _
'                    "WHERE id = " & txtExWarNum.text & "; "
'        Set rs = dbPredator.OpenRecordset(strQuery)
    
'        If rs.EOF Then
'           frmModWarrant.setWarrant (txtExWarNum)
'            frmAddRemModWar.Visible = False
'            frmModWarrant.Visible = True
'        Else
'            MsgBox ("The warrant cannot be created since a warrant with id " & txtExWarNum.text & " already exists")
'        End If
    
'        dbPredator.Close
'    Else
'        MsgBox ("Warrant number not a number")
'    End If
    
'***************** END OLD CODE *******************************

End Sub

Private Sub cmdModEx_Click()
  Dim i As Integer
  
  If Not IsNull(txtExWarNum.text) And IsNumeric(txtExWarNum.text) Then
    i = txtExWarNum.text
    frmModWarrant.setWarrant (i)
    
    If frmModWarrant.iWarrantNumber > -1 Then
        frmModWarrant.Visible = True
        frmAddRemModWar.Visible = False
    Else
        MsgBox ("Invalid Warrant Number")
    End If
  Else
      MsgBox ("Warrant number not a number")
  End If
    
    

End Sub

Private Sub Form_Unload(Cancel As Integer)
    frmAddRemModWar.Visible = False
    frmDataMan.Visible = True

End Sub

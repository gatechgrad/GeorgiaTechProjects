VERSION 5.00
Begin VB.Form frmWarNum 
   Caption         =   "Warrant Number"
   ClientHeight    =   2430
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   3435
   LinkTopic       =   "Form5"
   ScaleHeight     =   2430
   ScaleWidth      =   3435
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdDisplay 
      Caption         =   "Display Warrant"
      Height          =   495
      Left            =   1440
      TabIndex        =   4
      Top             =   1200
      Width           =   1575
   End
   Begin VB.CommandButton cmdNext 
      Caption         =   "Next..."
      Height          =   495
      Left            =   1440
      TabIndex        =   3
      Top             =   1800
      Width           =   1575
   End
   Begin VB.TextBox txtWarNum 
      Height          =   285
      Left            =   240
      TabIndex        =   1
      Top             =   720
      Width           =   1455
   End
   Begin VB.Label lbl2 
      Caption         =   "Warrant Number"
      Height          =   255
      Left            =   1800
      TabIndex        =   2
      Top             =   720
      Width           =   1455
   End
   Begin VB.Label lbl1 
      Caption         =   "Enter in the warrant number for which you wish to add/remove e-mail addresses:"
      Height          =   375
      Left            =   240
      TabIndex        =   0
      Top             =   120
      Width           =   3015
   End
End
Attribute VB_Name = "frmWarNum"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub cmdDisplay_Click()
  Dim i As Integer
  
  If Not IsNull(txtWarNum.text) And IsNumeric(txtWarNum.text) Then
    i = txtWarNum.text
    frmViewWar.setWarrant (i)
    
    If frmViewWar.iWarrantNumber > -1 Then
        frmViewWar.Visible = True
    Else
        MsgBox ("Please enter a valid warrant number")
    End If
  Else
    MsgBox ("Warrant number not a number")
  End If
    
End Sub

Private Sub cmdNext_Click()
  Dim i As Integer
  
  
  If Not IsNull(txtWarNum.text) And IsNumeric(txtWarNum.text) Then
    i = txtWarNum.text
    frmAddRemEmail.setWarrant (i)
    
    If frmAddRemEmail.iWarrantNumber > -1 Then
        frmWarNum.Visible = False
        frmAddRemEmail.Visible = True
    Else
        MsgBox ("Please enter a valid warrant number")
    End If

  Else
    MsgBox ("Warrant number not a number")
    
  End If
    


End Sub
Private Sub Form_Unload(Cancel As Integer)
  frmWarNum.Visible = False
  frmDataMan.Visible = True
End Sub

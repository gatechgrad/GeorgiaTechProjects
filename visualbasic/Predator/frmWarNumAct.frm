VERSION 5.00
Begin VB.Form frmWarNumAct 
   Caption         =   "Warrant Number"
   ClientHeight    =   2355
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   3090
   LinkTopic       =   "Form1"
   ScaleHeight     =   2355
   ScaleWidth      =   3090
   StartUpPosition =   3  'Windows Default
   Begin VB.TextBox txtWarNum 
      Height          =   285
      Left            =   0
      TabIndex        =   2
      Top             =   600
      Width           =   1455
   End
   Begin VB.CommandButton cmdNext 
      Caption         =   "Next..."
      Height          =   495
      Left            =   1200
      TabIndex        =   1
      Top             =   1680
      Width           =   1575
   End
   Begin VB.CommandButton cmdDisplay 
      Caption         =   "Display Warrant"
      Height          =   495
      Left            =   1200
      TabIndex        =   0
      Top             =   1080
      Width           =   1575
   End
   Begin VB.Label lbl1 
      Caption         =   "Enter in the warrant number for which you wish to check activity on:"
      Height          =   375
      Left            =   0
      TabIndex        =   4
      Top             =   0
      Width           =   3015
   End
   Begin VB.Label lbl2 
      Caption         =   "Warrant Number"
      Height          =   255
      Left            =   1560
      TabIndex        =   3
      Top             =   600
      Width           =   1455
   End
End
Attribute VB_Name = "frmWarNumAct"
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
    frmWarAct.setWarrant (i)
    
    If frmWarAct.iWarrantNumber > -1 Then
        frmWarNumAct.Visible = False
        frmWarAct.Visible = True
    Else
        MsgBox ("Please enter a valid warrant number")
    End If

  Else
    MsgBox ("Warrant number not a number")
    
  End If

End Sub
Private Sub Form_Unload(Cancel As Integer)
  frmWarNumAct.Visible = False
  frmDataAnal.Visible = True
End Sub


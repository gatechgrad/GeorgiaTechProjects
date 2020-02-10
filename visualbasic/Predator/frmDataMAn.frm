VERSION 5.00
Begin VB.Form frmDataMan 
   Caption         =   "Data Management"
   ClientHeight    =   3390
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   3855
   LinkTopic       =   "Form3"
   Picture         =   "frmDataMAn.frx":0000
   ScaleHeight     =   3390
   ScaleWidth      =   3855
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdRF 
      Caption         =   "Add/Remove Red-Flag Words"
      Height          =   495
      Left            =   120
      TabIndex        =   3
      Top             =   600
      Width           =   2535
   End
   Begin VB.CommandButton cmdWar 
      Caption         =   "Add/Remove/Modify Warrants"
      Height          =   495
      Left            =   120
      TabIndex        =   2
      Top             =   1800
      Width           =   2535
   End
   Begin VB.CommandButton cmdEmail 
      Caption         =   "Add/Remove E-Mail Addresses"
      Height          =   495
      Left            =   120
      TabIndex        =   1
      Top             =   1200
      Width           =   2535
   End
   Begin VB.CommandButton cmdCancel 
      Caption         =   "Cancel"
      Height          =   495
      Left            =   120
      TabIndex        =   0
      Top             =   2400
      Width           =   1095
   End
End
Attribute VB_Name = "frmDataMan"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub cmdCancel_Click()
    frmDataMan.Visible = False
    frmWelcome.Visible = True
End Sub

Private Sub cmdEmail_Click()
    frmDataMan.Visible = False
    frmWarNum.Visible = True
End Sub

Private Sub cmdRF_Click()
    frmDataMan.Visible = False
    frmRFMaint.Visible = True
End Sub

Private Sub cmdWar_Click()
    frmDataMan.Visible = False
    frmAddRemModWar.Visible = True
End Sub
Private Sub Form_Unload(Cancel As Integer)
    frmDataMan.Visible = False
    frmWelcome.Visible = True

End Sub

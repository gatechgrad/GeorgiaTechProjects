VERSION 5.00
Begin VB.Form frmDataAnal 
   Caption         =   "Predator Data Analysis"
   ClientHeight    =   4395
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4455
   LinkTopic       =   "Form4"
   Picture         =   "frmDataAnal.frx":0000
   ScaleHeight     =   4395
   ScaleWidth      =   4455
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdCancel 
      Caption         =   "Cancel"
      Height          =   495
      Left            =   120
      TabIndex        =   4
      Top             =   3600
      Width           =   1935
   End
   Begin VB.CommandButton cmdShady 
      Caption         =   "Find Shady Characters"
      Height          =   495
      Left            =   120
      TabIndex        =   3
      Top             =   2400
      Width           =   1935
   End
   Begin VB.CommandButton cmdRedFalg 
      Caption         =   "Red Flag Activity"
      Height          =   495
      Left            =   120
      TabIndex        =   2
      Top             =   1800
      Width           =   1935
   End
   Begin VB.CommandButton cmdWarAct 
      Caption         =   "Warrant Activity"
      Height          =   495
      Left            =   120
      TabIndex        =   1
      Top             =   1200
      Width           =   1935
   End
   Begin VB.CommandButton cmdAR 
      Caption         =   "Association Rules"
      Height          =   495
      Left            =   120
      TabIndex        =   0
      Top             =   3000
      Width           =   1935
   End
End
Attribute VB_Name = "frmDataAnal"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub cmdAR_Click()
    frmDataAnal.Visible = False
    frmAR.Visible = True

End Sub

Private Sub cmdCancel_Click()
    frmDataAnal.Visible = False
    frmWelcome.Visible = True

End Sub

Private Sub cmdRedFalg_Click()
    frmDataAnal.Visible = False
    frmRedSearch.reset
    frmRedSearch.Visible = True

End Sub

Private Sub cmdShady_Click()
    frmDataAnal.Visible = False
    frmShady.Visible = True

End Sub

Private Sub cmdWarAct_Click()
    frmDataAnal.Visible = False
    frmWarNumAct.Visible = True
End Sub

Private Sub Form_Unload(Cancel As Integer)
    frmDataAnal.Visible = False
    frmWelcome.Visible = True

End Sub

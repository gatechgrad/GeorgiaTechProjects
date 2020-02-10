VERSION 5.00
Begin VB.Form frmWelcome 
   Caption         =   "Welcome to Predator"
   ClientHeight    =   4080
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4260
   LinkTopic       =   "Form1"
   Picture         =   "frmWelcome.frx":0000
   ScaleHeight     =   4080
   ScaleWidth      =   4260
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdExit 
      Caption         =   "Exit Predator"
      Height          =   495
      Left            =   120
      TabIndex        =   4
      Top             =   2880
      Width           =   1575
   End
   Begin VB.CommandButton cmdDataAnal 
      Caption         =   "Data Analysis"
      Height          =   495
      Left            =   120
      TabIndex        =   3
      Top             =   2160
      Width           =   1575
   End
   Begin VB.CommandButton cmdDataMan 
      Caption         =   "Data Management"
      Height          =   495
      Left            =   120
      TabIndex        =   2
      Top             =   1440
      Width           =   1575
   End
   Begin VB.CommandButton cmdLoad 
      Caption         =   "Load Data "
      Height          =   495
      Left            =   120
      TabIndex        =   1
      Top             =   720
      Width           =   1575
   End
   Begin VB.Label lblFBS 
      BackStyle       =   0  'Transparent
      Caption         =   "The FBS is watching you..."
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   13.5
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   -1  'True
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H0000FF00&
      Height          =   495
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   4455
   End
End
Attribute VB_Name = "frmWelcome"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Const dbFile = "Predator.mdb"
Private Sub cmdDataAnal_Click()
    frmWelcome.Visible = False
    frmDataAnal.Visible = True
End Sub

Private Sub cmdDataMan_Click()
    frmWelcome.Visible = False
    frmDataMan.Visible = True
End Sub

Private Sub cmdExit_Click()
    End
End Sub
Private Sub Form_Unload(Cancel As Integer)
    End
End Sub


Private Sub cmdLoad_Click()
    frmWelcome.Visible = False
    frmPredUpload.Visible = True

End Sub

